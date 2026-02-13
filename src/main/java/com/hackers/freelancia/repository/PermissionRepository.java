package com.hackers.freelancia.repository;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.entity.Permission;

public interface PermissionRepository extends AbstractRepository<Permission, String>{

    boolean existsByName(String name);
}
