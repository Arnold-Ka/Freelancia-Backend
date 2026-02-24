package com.hackers.freelancia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackers.freelancia.security.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(String userId);
    void deleteByToken(String token);
}
