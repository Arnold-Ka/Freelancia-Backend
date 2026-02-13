package com.hackers.freelancia.repository;

import java.util.Optional;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.entity.Role;

public interface RoleRepository extends AbstractRepository<Role, String> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

}
