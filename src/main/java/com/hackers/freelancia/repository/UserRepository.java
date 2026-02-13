package com.hackers.freelancia.repository;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.entity.User;


public interface UserRepository extends AbstractRepository<User, String> {
   User findByUsername(String username);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);
}
