package com.hackers.freelancia.repository;

import java.util.List;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.entity.FreelanceExperience;

public interface FreelanceExperienceRepository extends AbstractRepository<FreelanceExperience, String> {

    List<FreelanceExperience> findByFreelanceProfileId(String freelanceProfileId);
}
