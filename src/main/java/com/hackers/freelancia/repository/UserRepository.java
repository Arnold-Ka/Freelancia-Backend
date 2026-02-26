package com.hackers.freelancia.repository;

import java.util.Optional;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.User;

public interface UserRepository extends AbstractRepository<User, String> {
   Optional<User> findByUsernameAndStatut(String username, Statut statut);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);

   User findByEmail(String email);

   Optional<User> findByUsername(String username);
}
