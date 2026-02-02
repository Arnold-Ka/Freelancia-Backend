package com.hackers.freelancia.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.config.Utils;
import com.hackers.freelancia.dto.ReviewDto;
import com.hackers.freelancia.dto.SkillsDto;
import com.hackers.freelancia.entity.Review;
import com.hackers.freelancia.entity.Skills;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.repository.MissionRepository;
import com.hackers.freelancia.repository.ReviewRepository;
import com.hackers.freelancia.repository.SkillsRepository;
import com.hackers.freelancia.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service générale de l'application.
 *
 * @author : <A HREF="mailto:karambiriarnold@gmail.com">Karambiri Lawatan Arnold Bily</A>
 * @version : 1.0
 * Copyright (c) 2021 All rights reserved.
 * @since : 14/05/2021 à 13:51
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FreelanciaService {

    private final MissionRepository missionRepository;

    private final UserRepository userRepository;

    private final SkillsRepository skillsRepository;
    private final ReviewRepository reviewRepository;
    private final Mapper mapper;


    /**
     * Récuperation des skills actives
     * @return la liste de skills
     */
    public List<SkillsDto> getSkills() {
        return skillsRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    /**
     * Récuperation d'un Skills a partir de son id
     * @param id l'identifiant
     * @return le skills
     */
    public SkillsDto getSkill(final String id){
        return mapper.maps(skillsRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ));
    }

    /**
     * Enregistrement d'un skills dans la DB
     * @param skillsDto les nouvelles informations sur le skills
     */
    public void postSkill(final SkillsDto skillsDto){
        if (skillsDto.getName() == null || skillsDto.getName() == "") {
            new IllegalArgumentException("Le nom est vide");
        }
        Skills skills = mapper.maps(skillsDto);
        skills.setId(Utils.generateId());
        skillsRepository.save(skills);
    }

    /**
     * Modification des information du skill dont l'id a éte fourni
     * @param id l'identifiant
     * @param skillsDto les nouvelles informations
     */
    public void putSkill(final String id, final SkillsDto skillsDto) {
        Skills skills = mapper.maps(skillsDto);
        skills.setId(id);
        skillsRepository.save(skills);
    }

    /**
     * Mettre le statut du skills dont le id est passer a Supprimer
     * @param id l'identifiant
     */
    public void deleteSkill(final String id) {
        Skills skills = skillsRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
           () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skills non Trouvé")
        );
        skills.setStatut(Statut.DELETED);
        skillsRepository.save(skills);
    }

    /**
     * Récupertaion des reviews Actives.
     * @return la liste des review
     */
    public List<ReviewDto> getviews(){
        return reviewRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    /**
     * Récuperation d'un review graca a son identifiant
     * @param id l'identfiant
     * @return la review
     */
    public ReviewDto getReview(final String id){
        return mapper.maps(reviewRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review non Trové")
        ));
    }
    /**
     * Création d'un review
     * @param reviewDto les données de la review
     */
    public void postReview(final ReviewDto reviewDto){
        if (!userRepository.existsById(reviewDto.getUserId())) {
            new IllegalArgumentException("Utilisateur non Trové");
        }
        if (!missionRepository.existsById(reviewDto.getMissionId())) {
            new IllegalArgumentException("Mission non trouvé");
        }
        Review review = mapper.maps(reviewDto);
        review.setId(Utils.generateId());
        reviewRepository.save(review);
    }
    public void PutReview(final String id, final ReviewDto reviewDto){

    }

}
