package com.hackers.freelancia.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.hackers.freelancia.config.BaseEntity;
import com.hackers.freelancia.entity.enumeration.StatutMission;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "missions")
@Entity
@Getter
@Setter
public class Mission extends BaseEntity{

    private String title;
    private String description;
    private Double price;
    private StatutMission statutMission;
    private LocalDate deadline;



    @ManyToOne(optional = false)
    private FreelanceProfile freelanceProfile;
    @ManyToOne(optional = false)
    private User client;
    @ManyToMany
    @JoinTable(
            name = "mission_skills",
            joinColumns = @JoinColumn(name = "mission_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    private Set<Skills> skills = new HashSet<>();

}
