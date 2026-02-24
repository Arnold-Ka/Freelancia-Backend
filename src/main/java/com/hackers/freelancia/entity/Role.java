package com.hackers.freelancia.entity;

import java.util.Set;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private String name;

    @ManyToMany
    private Set<Permission> permissions;
    

}
