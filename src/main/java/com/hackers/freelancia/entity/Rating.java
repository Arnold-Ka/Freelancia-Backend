package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {
    private Long ratingValue;
    private String comment;

    @ManyToOne
    private FreelanceProfile freelanceProfile;

    public Long getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Long ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FreelanceProfile getFreelanceProfile() {
        return freelanceProfile;
    }

    public void setFreelanceProfile(FreelanceProfile freelanceProfile) {
        this.freelanceProfile = freelanceProfile;
    }

}
