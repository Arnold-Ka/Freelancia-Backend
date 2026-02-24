package com.hackers.freelancia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.config.Utils;
import com.hackers.freelancia.dto.CategoryDto;
import com.hackers.freelancia.dto.MissionDto;
import com.hackers.freelancia.dto.PaymentDto;
import com.hackers.freelancia.dto.ReviewDto;
import com.hackers.freelancia.dto.SkillsDto;
import com.hackers.freelancia.dto.SubscriptionDto;
import com.hackers.freelancia.entity.Category;
import com.hackers.freelancia.entity.Mission;
import com.hackers.freelancia.entity.Payment;
import com.hackers.freelancia.entity.Review;
import com.hackers.freelancia.entity.Skills;
import com.hackers.freelancia.entity.Subscription;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.repository.CategoryRepository;
import com.hackers.freelancia.repository.MissionRepository;
import com.hackers.freelancia.repository.PaymentRepository;
import com.hackers.freelancia.repository.ReviewRepository;
import com.hackers.freelancia.repository.SkillsRepository;
import com.hackers.freelancia.repository.SubscriptionRepository;
import com.hackers.freelancia.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service générale de l'application.
 *
 * @author : <A HREF="mailto:karambiriarnold@gmail.com">Karambiri Lawatan Arnold
 *         Bily</A>
 * @version : 1.0
 *          Copyright (c) 2021 All rights reserved.
 * @since : 14/05/2021 à 13:51
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FreelanciaService {

    private final MissionRepository missionRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;
    private final SkillsRepository skillsRepository;
    private final ReviewRepository reviewRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final Mapper mapper;

    /**
     * Récuperation d'une category graca a son identifiant
     * 
     * @param id l'identfiant
     * @return la category
     */
    public CategoryDto getCategory(final String id) {
        return mapper.maps(categoryRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category non Trouvée")));
    }

    /**
     * Récuperation des categories Actives.
     * 
     * @return la liste des categories
     */
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    /**
     * Enregistrement d'une category dans la DB
     * 
     * @param categoryDto les nouvelles informations sur la category
     */
    public void postCategory(final CategoryDto categoryDto) {
        if (categoryDto.getName() == null || categoryDto.getName() == "") {
            new IllegalArgumentException("Le nom est vide");
        }
        if (categoryRepository.existsByNameAndStatut(categoryDto.getName(), Statut.ACTIVE)) {
            new IllegalArgumentException("Category déjà existante");
        }
        Category category = mapper.maps(categoryDto);
        category.setId(Utils.generateId());
        categoryRepository.save(category);
    }

    /**
     * Modification des information de la category dont l'id a éte fourni
     * 
     * @param id          l'identifiant
     * @param categoryDto les nouvelles informations
     */
    public void putCategory(final String id, final CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            new IllegalArgumentException("Category non Trouvée");
        }
        if (categoryDto.getName() == null || categoryDto.getName() == "") {
            new IllegalArgumentException("Le nom est vide");
        }
        if (categoryRepository.existsByNameAndStatut(categoryDto.getName(), Statut.ACTIVE)) {
            new IllegalArgumentException("Category déjà existante");
        }
        Category category = mapper.maps(categoryDto);
        category.setId(id);
        categoryRepository.save(category);
    }

    /**
     * Mettre le statut de la category dont le id est passer a Supprimer
     * 
     * @param id l'identifiant
     */
    public void deleteCategory(final String id) {
        Category category = categoryRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category non Trouvée"));
        category.setStatut(Statut.DELETED);
        categoryRepository.save(category);
    }

    /**
     * Récuperation des skills actives
     * 
     * @return la liste de skills
     */
    public List<SkillsDto> getSkills() {
        return skillsRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    /**
     * Récuperation d'un Skills a partir de son id
     * 
     * @param id l'identifiant
     * @return le skills
     */
    public SkillsDto getSkill(final String id) {
        return mapper.maps(skillsRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * Enregistrement d'un skills dans la DB
     * 
     * @param skillsDto les nouvelles informations sur le skills
     */
    public void postSkill(final SkillsDto skillsDto) {
        if (skillsDto.getName() == null || skillsDto.getName() == "") {
            new IllegalArgumentException("Le nom est vide");
        }
        Skills skills = mapper.maps(skillsDto);
        skills.setId(Utils.generateId());
        skillsRepository.save(skills);
    }

    /**
     * Modification des information du skill dont l'id a éte fourni
     * 
     * @param id        l'identifiant
     * @param skillsDto les nouvelles informations
     */
    public void putSkill(final String id, final SkillsDto skillsDto) {
        Skills skills = mapper.maps(skillsDto);
        skills.setId(id);
        skillsRepository.save(skills);
    }

    /**
     * Mettre le statut du skills dont le id est passer a Supprimer
     * 
     * @param id l'identifiant
     */
    public void deleteSkill(final String id) {
        Skills skills = skillsRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skills non Trouvé"));
        skills.setStatut(Statut.DELETED);
        skillsRepository.save(skills);
    }

    /**
     * Récupertaion des reviews Actives.
     * 
     * @return la liste des review
     */
    public List<ReviewDto> getReviews() {
        return reviewRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    /**
     * Récuperation d'un review graca a son identifiant
     * 
     * @param id l'identfiant
     * @return la review
     */
    public ReviewDto getReview(final String id) {
        return mapper.maps(reviewRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review non Trové")));
    }

    /**
     * Création d'un review
     * 
     * @param reviewDto les données de la review
     */
    public void postReview(final ReviewDto reviewDto) {
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

    /**
     * Modification d'une review
     * 
     * @param id        l'identifiant de la review a modifier
     * @param reviewDto les nouvelles données de la review
     */
    public void PutReview(final String id, final ReviewDto reviewDto) {
        if (!userRepository.existsById(reviewDto.getUserId())) {
            new IllegalArgumentException("Utilisateur non Trové");
        }
        if (!missionRepository.existsById(reviewDto.getMissionId())) {
            new IllegalArgumentException("Mission non trouvé");
        }
        Review review = mapper.maps(reviewDto);
        review.setId(id);
        reviewRepository.save(review);
    }

    /**
     * Mettre le statut de la review dont le id est passer a Supprimer
     * 
     * @param id l'identifiant
     */
    public void deleteReview(final String id) {
        Review review = reviewRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review non Trouvé"));
        review.setStatut(Statut.DELETED);
        reviewRepository.save(review);
    }

    /**
     * Récuperation d'une subscription graca a son identifiant
     * 
     * @param id l'identfiant
     * @return la subscription
     */
    public SubscriptionDto getSubscription(final String id) {
        return mapper.maps(subscriptionRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription non Trové")));
    }

    public List<SubscriptionDto> getSubscriptions(final String profileId) {
        if (!userRepository.existsById(profileId)) {
            throw new IllegalArgumentException("Utilisateur non Trové");
        }
        return subscriptionRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    /**
     * Création d'une subscription
     * 
     * @param subscriptionDto les données de la subscription
     */
    public List<SubscriptionDto> getSubscriptions() {
        return subscriptionRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    public void postSubscription(final SubscriptionDto subscriptionDto) {
        if (!userRepository.existsById(subscriptionDto.getFreelanceProfileId())) {
            throw new IllegalArgumentException("Utilisateur non Trové");
        }
        Subscription subscription = mapper.maps(subscriptionDto);
        subscription.setId(Utils.generateId());
        subscriptionRepository.save(subscription);
    }

    /**
     * Modification d'une subscription
     * 
     * @param id              l'identifiant de la subscription a modifier
     * @param subscriptionDto les nouvelles données de la subscription
     */
    public void PutSubscription(final String id, final SubscriptionDto subscriptionDto) {
        if (!subscriptionRepository.existsById(id)) {
            throw new IllegalArgumentException("Subscription non Trové");
        }
        if (!userRepository.existsById(subscriptionDto.getFreelanceProfileId())) {
            throw new IllegalArgumentException("Utilisateur non Trové");
        }
        Subscription subscription = mapper.maps(subscriptionDto);
        subscription.setId(id);
        subscriptionRepository.save(subscription);
    }

    /**
     * Mettre le statut de la subscription dont le id est passer a Supprimer
     * 
     * @param id l'identifiant
     */
    public void deleteSubscription(final String id) {
        Subscription subscription = subscriptionRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription non Trouvé"));
        subscription.setStatut(Statut.DELETED);
        subscriptionRepository.save(subscription);
    }

    /**
     * Récuperation d'un payment graca a son identifiant
     * 
     * @param id l'identfiant
     * @return le payment
     */
    public PaymentDto getPayment(final String id) {
        return mapper.maps(paymentRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment non Trové")));
    }

    /**
     * Récuperation des payments Actives.
     * 
     * @return la liste des payments
     */
    public List<PaymentDto> getPayments() {
        return paymentRepository.findAllActive().stream().map(mapper::maps).toList();
    }

    /**
     * Récuperation des payments Actives d'une mission.
     * 
     * @return la liste des payments
     */
    public List<PaymentDto> getPaymentsByMissionId(final String missionId) {
        return paymentRepository.findByMissionIdAndStatut(missionId, Statut.ACTIVE).stream().map(mapper::maps).toList();
    }

    /**
     * Création d'un payment
     * 
     * @param paymentDto les données de la payment
     */
    public void postPayment(final PaymentDto paymentDto) {
        if (!missionRepository.existsById(paymentDto.getMissionId())) {
            throw new IllegalArgumentException("Mission non Trouvé");
        }
        Payment payment = mapper.maps(paymentDto);
        payment.setId(Utils.generateId());
        paymentRepository.save(payment);
    }

    /**
     * Modification d'une payment
     * 
     * @param id         l'identifiant de la payment a modifier
     * @param paymentDto les nouvelles données de la payment
     */
    public void PutPayment(final String id, final PaymentDto paymentDto) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Payment non Trouvé");
        }
        if (!missionRepository.existsById(paymentDto.getMissionId())) {
            throw new IllegalArgumentException("Mission non Trouvé");
        }
        Payment payment = mapper.maps(paymentDto);
        payment.setId(id);
        paymentRepository.save(payment);
    }

    /**
     * Mettre le statut de la payment dont le id est passer a Supprimer
     * 
     * @param id l'identifiant
     */
    public void deletePayment(final String id) {
        Payment payment = paymentRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment non Trouvé"));
        payment.setStatut(Statut.DELETED);
        paymentRepository.save(payment);
    }

    /**
     * Récuperation d'une mission graca a son identifiant
     * 
     * @param id l'identfiant
     * @return la mission
     */
    public MissionDto getMission(final String id) {
        Mission mission = missionRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission non Trouvé"));
        Set<Skills> skills = mission.getSkills();
        Set<String> skillsIds = skills.stream().map(Skills::getId).collect(Collectors.toSet());
        MissionDto missionDto = mapper.maps(mission);
        missionDto.setSkillsId(skillsIds);
        return missionDto;
    }

    /**
     * Récuperation des missions Actives.
     * 
     * @return la liste des missions
     */
    public List<MissionDto> getMissions() {
        List<Mission> missions = missionRepository.findAllActive();
        List<MissionDto> missionDtos = new ArrayList<>();
        for (Mission mission : missions) {
            Set<Skills> skills = mission.getSkills();
            Set<String> skillsIds = skills.stream().map(Skills::getId).collect(Collectors.toSet());
            MissionDto missionDto = mapper.maps(mission);
            missionDto.setSkillsId(skillsIds);
            missionDtos.add(missionDto);
        }

        return missionDtos;
    }

    /**
     * Création d'une mission
     * 
     * @param missionDto les données de la mission
     */
    public void postMission(final MissionDto missionDto) {
        if (!userRepository.existsById(missionDto.getClientId())) {
            throw new IllegalArgumentException("Utilisateur non Trouvé");
        }
        if (!userRepository.existsById(missionDto.getFreelanceProfileId())) {
            throw new IllegalArgumentException("Utilisateur non Trouvé");
        }
        Set<String> skillsIds = missionDto.getSkillsId();
        Set<Skills> skills = skillsRepository.findAllById(skillsIds).stream().filter(s -> s.isActive())
                .collect(java.util.stream.Collectors.toSet());
        Mission mission = mapper.maps(missionDto);
        mission.setId(Utils.generateId());
        mission.setSkills(skills);
        missionRepository.save(mission);
    }

    /**
     * Modification d'une mission
     * 
     * @param id         l'identifiant de la mission a modifier
     * @param missionDto les nouvelles données de la mission
     */
    public void PutMission(final String id, final MissionDto missionDto) {
        if (!missionRepository.existsById(id)) {
            throw new IllegalArgumentException("Mission non Trouvé");
        }
        if (!userRepository.existsById(missionDto.getClientId())) {
            throw new IllegalArgumentException("Utilisateur non Trouvé");
        }
        if (!userRepository.existsById(missionDto.getFreelanceProfileId())) {
            throw new IllegalArgumentException("Utilisateur non Trouvé");
        }
        Set<String> skillsIds = missionDto.getSkillsId();
        Set<Skills> skills = skillsRepository.findAllById(skillsIds).stream().filter(s -> s.isActive())
                .collect(java.util.stream.Collectors.toSet());

        Mission mission = mapper.maps(missionDto);
        mission.setId(id);
        mission.setSkills(skills);
        missionRepository.save(mission);
    }

    /**
     * Mettre le statut de la mission dont le id est passer a Supprimer
     * 
     * @param id l'identifiant
     */
    public void deleteMission(final String id) {
        Mission mission = missionRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mission non Trouvé"));
        mission.setStatut(Statut.DELETED);
        missionRepository.save(mission);
    }

}
