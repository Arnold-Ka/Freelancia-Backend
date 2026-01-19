package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "skills")

public class Skills extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    private String category;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "icon", nullable = true)
    private String icon;
}
