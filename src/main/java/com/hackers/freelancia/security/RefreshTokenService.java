package com.hackers.freelancia.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hackers.freelancia.entity.User;
import com.hackers.freelancia.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refresh-expiration}")
    private long refreshTokenDurationMs;
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Crée un nouveau token de rafraîchissement pour un utilisateur donné.
     *
     * @param user l'utilisateur pour lequel créer le token de rafraîchissement
     * @return le token de rafraîchissement créé
     * 
     * @throws RuntimeException si une erreur se produit lors de la création du token de rafraîchissement
     */
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Supprime tous les tokens de rafraîchissement associés à un utilisateur donné.
     *
     * @param userId l'ID de l'utilisateur pour lequel supprimer les tokens de rafraîchissement
     */
    public void deleteByUserId(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
    
    /**
     * Vérifie si un token de rafraîchissement est expiré et le supprime s'il l'est.
     *
     * @param token le token de rafraîchissement à vérifier
     * @return le token de rafraîchissement s'il n'est pas expiré
     * 
     * @throws RuntimeException si le token de rafraîchissement est expiré
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

    /**
     * Supprime un token de rafraîchissement par son token string.
     *
     * @param token le token de rafraîchissement à supprimer
     */
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    /**
     * Trouve un token de rafraîchissement par son token string.
     *
     * @param token le token de rafraîchissement à trouver
     * @return une option contenant le token de rafraîchissement trouvé, ou vide s'il n'est pas trouvé
     */   
    public java.util.Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

}
