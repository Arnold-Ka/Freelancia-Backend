package com.hackers.freelancia.entity;

import java.util.Set;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private String name;

    @OneToMany
    private Set<Permission> permissions;
    

}
