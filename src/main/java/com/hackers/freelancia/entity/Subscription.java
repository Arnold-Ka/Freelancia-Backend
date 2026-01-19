package com.hackers.freelancia.entity;


import java.time.LocalDate;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "subscriptions")
@Entity
@Getter
@Setter
public class Subscription extends BaseEntity{
    @ManyToOne
    private FreelanceProfile freelanceProfile;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
    private String type;// A changer le String par un enum

}
