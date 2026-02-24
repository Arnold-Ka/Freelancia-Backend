package com.hackers.freelancia.repository;

import java.util.Set;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.entity.FreelanceSkills;

public interface FreelanceSkillsRepository extends AbstractRepository<FreelanceSkills, String>{
    Set<FreelanceSkills> findByFreelanceProfileId(String freelanceProfileId);
    Set<FreelanceSkills> findBySkillsId(String freelanceSkillsId);
}
