package com.hackers.freelancia.repository;

import java.util.Optional;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.Role;

public interface RoleRepository extends AbstractRepository<Role, String> {

    Optional<Role> findByNameAndStatut(String name, Statut statut);

    boolean existsByName(String name);

}
