package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "reviews")
@Entity
@Getter
@Setter
public class Review extends BaseEntity{

    private String comment;
    private Long rating;

    @ManyToOne
    private FreelanceProfile freelanceProfile;
    @ManyToOne
    private User user;
    @ManyToOne
    private Mission mission;

}
