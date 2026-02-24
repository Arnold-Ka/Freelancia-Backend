package com.hackers.freelancia.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hackers.freelancia.entity.ActivationToken;
import com.hackers.freelancia.entity.PasswordResetToken;
import com.hackers.freelancia.entity.User;
import com.hackers.freelancia.repository.ActivationTokenRepository;
import com.hackers.freelancia.repository.PasswordResetTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final ActivationTokenRepository activationRepo;
    private final PasswordResetTokenRepository resetRepo;

    /**
     * Génère un token d'activation pour un utilisateur donné.
     *
     * @param user l'utilisateur pour lequel générer le token
     * @return le token d'activation généré
     */
    public String generateActivationToken(User user) {
        String token = UUID.randomUUID().toString();

        ActivationToken entity = new ActivationToken();
        entity.setToken(token);
        entity.setUser(user);
        entity.setExpiresAt(LocalDateTime.now().plusHours(24));

        activationRepo.save(entity);
        return token;
    }

    /**
     * Génère un token de réinitialisation de mot de passe pour un utilisateur
     * donné.
     *
     * @param user l'utilisateur pour lequel générer le token
     * @return le token de réinitialisation de mot de passe généré
     */
    public String generateResetToken(User user) {
        String token = UUID.randomUUID().toString();

        PasswordResetToken entity = new PasswordResetToken();
        entity.setToken(token);
        entity.setUser(user);
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(30));

        resetRepo.save(entity);
        return token;
    }

    /**
     * Trouve un utilisateur associé à un token de réinitialisation de mot de passe
     * donné.
     *
     * @param token le token de réinitialisation de mot de passe
     * @return un Optional contenant l'utilisateur associé au token, ou vide si le
     *         token est invalide ou expiré
     */
    public Optional<User> findUserByResetToken(String token) {
        return resetRepo.findByToken(token)
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(PasswordResetToken::getUser);
    }

    /**
     * Trouve un utilisateur associé à un token d'activation donné.
     *
     * @param token le token d'activation
     * @return un Optional contenant l'utilisateur associé au token, ou vide si le
     *         token est invalide ou expiré
     */
    public Optional<User> findUserByActivationToken(String token) {
        return activationRepo.findByToken(token)
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(ActivationToken::getUser);
    }

    /**
     * Supprime un token d'activation donné.
     *
     * @param token le token d'activation à supprimer
     */
    public void deleteActivationToken(String token) {
        activationRepo.findByToken(token).ifPresent(activationRepo::delete);
    }

    /**
     * Supprime un token de réinitialisation de mot de passe donné.
     *
     * @param token le token de réinitialisation de mot de passe à supprimer
     */
    public void deleteResetToken(String token) {
        resetRepo.findByToken(token).ifPresent(resetRepo::delete);
    }
}
