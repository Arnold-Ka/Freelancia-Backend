package com.hackers.freelancia.entity;

import java.util.HashSet;
import java.util.Set;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "freelance_profiles")
public class FreelanceProfile extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "bio", nullable = true, length = 2000)
    private String bio;
    @Column(name = "city", nullable = true)
    private String city;
    @Column(name = "country", nullable = true)
    private String country;
    @Column(name = "portfolio_url", nullable = true)
    private String portfolioUrl;
    @Column(name = "links", nullable = true)
    private Set<String> links = new HashSet<>();
    @Column(name = "rating", nullable = true)
    private Long rating;
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;
    @Column(name = "is_premium", nullable = false)
    private boolean isPremium;
    
    @OneToOne
    private User user;
}
