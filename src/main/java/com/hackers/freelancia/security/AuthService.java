package com.hackers.freelancia.security;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.Role;
import com.hackers.freelancia.entity.User;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.service.EmailService;
import com.hackers.freelancia.service.TokenService;
import com.hackers.freelancia.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final Mapper mapper;

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param request les informations d'inscription de l'utilisateur
     * @return une réponse d'authentification contenant un message de succès
     */
    public AuthResponse register(RegisterRequest request) {

        if (userService.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Username already taken");
        }

        if (userService.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Email already used");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = userService.getByName("USER");
        user.setRoles(Set.of(userRole));

        userService.postUser(mapper.maps(user));

        User savedUser = userService.loadUserByUsername(user.getUsername());

        String activationToken = tokenService.generateActivationToken(savedUser);

        try {
            emailService.sendActivationEmail(savedUser.getEmail(), activationToken);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }

        return new AuthResponse(
                "Compte créé. Vérifiez votre email pour activer votre compte.",
                null);
    }

    /**
     * Authentifie un utilisateur et génère un token JWT et un token de
     * rafraîchissement.
     *
     * @param request les informations d'authentification de l'utilisateur
     * @return une réponse d'authentification contenant le token JWT et le token de
     *         rafraîchissement
     */
    public AuthResponse login(AuthRequest request) {

        User user = (User) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()))
                .getPrincipal();

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    /**
     * Rafraîchit un token JWT à l'aide d'un token de rafraîchissement valide.
     *
     * @param request les informations de rafraîchissement du token
     * @return une réponse d'authentification contenant le nouveau token JWT et le
     *         même token de rafraîchissement
     */
    public AuthResponse refreshToken(RefreshRequest request) {

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenService.verifyExpiration(refreshToken);

        UserDetails userDetails = userService.loadUserByUsername(refreshToken.getUser().getUsername());
        String newAccessToken = jwtService.generateToken(userDetails);

        return new AuthResponse(newAccessToken, refreshToken.getToken());
    }

    /**
     * Envoie un email de réinitialisation de mot de passe à l'utilisateur.
     *
     * @param email l'adresse email de l'utilisateur qui a oublié son mot de passe
     */
    public void forgotPassword(String email) {

        User user = userService.getByEmail(email);

        if (user == null) {
            throw new RuntimeException("Email introuvable");
        }

        String resetToken = tokenService.generateResetToken(user);

        try {
            emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
        } catch (Exception e) {
            throw new RuntimeException("Erreur envoi email reset", e);
        }
    }

    /**
     * Réinitialise le mot de passe d'un utilisateur à l'aide d'un token de
     * réinitialisation valide.
     *
     * @param token       le token de réinitialisation envoyé par email
     * @param newPassword le nouveau mot de passe à définir
     */
    public void resetPassword(String token, String newPassword) {

        User user = tokenService.findUserByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré"));

        user.setPassword(passwordEncoder.encode(newPassword));

        userService.putUser(user.getId(), mapper.maps(user));

        tokenService.deleteResetToken(token);
    }

    /**
     * Active le compte d'un utilisateur à l'aide d'un token d'activation valide.
     *
     * @param token le token d'activation envoyé par email
     */
    public void activateAccount(String token) {
        User user = tokenService.findUserByActivationToken(token)
                .orElseThrow(() -> new RuntimeException("Token d'activation invalide ou expiré"));

        if (user.getStatut() == Statut.ACTIVE) {
            throw new RuntimeException("Compte déjà activé");
        }

        user.setStatut(Statut.ACTIVE);
        userService.putUser(user.getId(), mapper.maps(user));

        tokenService.deleteActivationToken(token);
    }

    /**
     * Déconnecte un utilisateur en supprimant son token de rafraîchissement.
     *
     * @param request les informations de rafraîchissement du token à supprimer
     */
    public void logout(RefreshRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
    }
}
