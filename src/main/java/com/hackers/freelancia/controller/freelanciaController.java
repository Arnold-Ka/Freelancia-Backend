package com.hackers.freelancia.controller;

import java.util.List;


import com.hackers.freelancia.dto.SkillsDto;
import com.hackers.freelancia.service.FreelanciaService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Controller générale de l'application.
 *
 * @author : <A HREF="mailto:karambiriarnold@gmail.com">Karambiri Lawatan Arnold Bily</A>
 * @version : 1.0
 * Copyright (c) 2021 All rights reserved.
 * @since : 25/01/2025 à 19:22
 */
@RestController
@RequiredArgsConstructor
public class freelanciaController {
    public static FreelanciaService service;

    /**
     * Récuperations des skills.
     * @return la liste de skills
     */
    @GetMapping("/skills")
    public List<SkillsDto> getAllSkills(){
        return service.getSkills();
    }
    /**
     * Recupéré un seul skills grace a son identifiant.
     * @param id l'identifiant
     * @return le skills
     */
    @GetMapping("/skills/{id}")
    public SkillsDto getSkillById(@PathVariable final String id){
        return service.getSkill(id);
    }

    /**
     * Création d'un skills
     * @param skillsDto les données sur le skill
     */
    @PostMapping("/skills")
    public void createSkill(@RequestBody final SkillsDto skillsDto){
        service.postSkill(skillsDto);
    }

    /**
     * Modification des informations d'un skills
     * @param id l'identifiant
     * @param skillsDto les nouvelles données sur le skills
     */ 
    @PutMapping("/skills/{id}")
    public void updateSkills(@PathVariable final String id, @RequestBody final SkillsDto skillsDto) {
        service.putSkill(id, skillsDto);
    }

    /**
     * Suppression d'un skills
     * @param id l'identifiant
     */
    @DeleteMapping("/skills/{id}")
    public void deleteSkill(@PathVariable final String id){
        service.deleteSkill(id);
    }
}
