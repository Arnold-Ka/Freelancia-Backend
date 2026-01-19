package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "freelance_experiences")
public class freelanceExperiance extends BaseEntity{

}
