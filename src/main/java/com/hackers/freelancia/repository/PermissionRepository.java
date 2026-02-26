package com.hackers.freelancia.repository;

import java.util.Optional;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.Permission;

public interface PermissionRepository extends AbstractRepository<Permission, String>{

    boolean existsByName(String name);

    Optional<Permission> findByNameAndStatut(String name, Statut statut);
}
