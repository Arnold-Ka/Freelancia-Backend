package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "freelance_experiences")
public class FreelanceExperience extends BaseEntity{
    private String title;
    private String description;
    private String company;
    private String location;
    private String startDate;
    private String endDate;
    @ManyToOne
    private FreelanceProfile freelanceProfile;
}
