package com.hackers.freelancia.entity;


import java.time.LocalDate;

import com.hackers.freelancia.config.BaseEntity;
import com.hackers.freelancia.entity.enumeration.TypeSubscription;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private TypeSubscription type;

}
