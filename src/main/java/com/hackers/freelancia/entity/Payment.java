package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;
import com.hackers.freelancia.entity.enumeration.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "payments")
@Entity
@Getter
@Setter
public class Payment extends BaseEntity{

    private Long amount;
    private Long commission;
    private String payementMethode;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    private Mission mission;

}
