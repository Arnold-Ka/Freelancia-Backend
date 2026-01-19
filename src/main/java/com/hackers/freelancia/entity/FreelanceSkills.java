package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "freelance_skills")
public class FreelanceSkills extends BaseEntity{
    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    private FreelanceProfile freelanceProfile;
    @ManyToOne
    private Skills skills;

}
