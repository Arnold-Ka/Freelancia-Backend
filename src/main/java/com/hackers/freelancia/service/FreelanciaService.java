package com.hackers.freelancia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackers.freelancia.entity.Skills;
import com.hackers.freelancia.repository.SkillsRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FreelanciaService {

    private final SkillsRepository skillsRepository;

    public List<Skills> getSkills() {
        return skillsRepository.findAll();
    }
}
