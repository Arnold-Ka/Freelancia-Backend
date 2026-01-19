package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
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
    private String status;

    @ManyToOne
    private Mission mission;

}
