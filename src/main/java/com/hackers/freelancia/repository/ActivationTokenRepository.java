package com.hackers.freelancia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackers.freelancia.entity.ActivationToken;

public interface ActivationTokenRepository extends JpaRepository<com.hackers.freelancia.entity.ActivationToken, Long> {
    Optional<ActivationToken> findByToken(String token);

}
