package com.hackers.freelancia.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreelanceProfileDto {
    private String id;
    private String firstName;
    private String lastName;
    private String bio;
    private String city;
    private String country;
    private Set<String> links;
    private Long rating;
    private boolean isVerified;
    private boolean isPremium;
    private String experience;
    private String portfolioUrl;
    private String userId;

}
