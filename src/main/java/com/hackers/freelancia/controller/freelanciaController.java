package com.hackers.freelancia.controller;

import java.util.List;
import java.util.Set;

import com.hackers.freelancia.dto.CategoryDto;
import com.hackers.freelancia.dto.FreelanceExperienceDto;
import com.hackers.freelancia.dto.FreelanceProfileDto;
import com.hackers.freelancia.dto.FreelanceSkillsDto;
import com.hackers.freelancia.dto.MissionDto;
import com.hackers.freelancia.dto.PaymentDto;
import com.hackers.freelancia.dto.RatingDto;
import com.hackers.freelancia.dto.ReviewDto;
import com.hackers.freelancia.dto.SkillsDto;
import com.hackers.freelancia.dto.SubscriptionDto;
import com.hackers.freelancia.dto.UserDto;
import com.hackers.freelancia.service.FreelanceProfileService;
import com.hackers.freelancia.service.FreelanciaService;
import com.hackers.freelancia.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final FreelanciaService service;
    private final UserService userService;
    private final FreelanceProfileService freelanceService;


    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> fetchCategory(@PathVariable final String id){
        return ResponseEntity.ok(service.getCategory(id));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> fetchCategories() {
        return  ResponseEntity.ok(service.getCategories());
    }

    @PostMapping("/categories")
    public ResponseEntity<String> createCategory(@RequestBody final CategoryDto categoryDto){
        service.postCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category Crée avec succèes");
    }
    
    @PutMapping("/categories/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable final String id, @RequestBody final CategoryDto categoryDto){
        service.putCategory(id, categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body("Categorie mis a jour avec succès");
    }

    public ResponseEntity<String> dropCategory(@PathVariable final String id){
        service.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category Sypprimer avec Succès");
    }
    
    /**
     * Récuperations des skills.
     * @return la liste de skills
     */
    @GetMapping("/skills")
    public ResponseEntity<List<SkillsDto>> getAllSkills(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getSkills());
    }
    /**
     * Recupéré un seul skills grace a son identifiant.
     * @param id l'identifiant
     * @return le skills
     */
    @GetMapping("/skills/{id}")
    public ResponseEntity<SkillsDto> getSkillById(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getSkill(id));
    }

    /**
     * Création d'un skills
     * @param skillsDto les données sur le skill
     */
    @PostMapping("/skills")
    public ResponseEntity<String> createSkill(@RequestBody final SkillsDto skillsDto){
        service.postSkill(skillsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Skill created successfully");
    }

    /**
     * Modification des informations d'un skills
     * @param id l'identifiant
     * @param skillsDto les nouvelles données sur le skills
     */ 
    @PutMapping("/skills/{id}")
    public ResponseEntity<String> updateSkills(@PathVariable final String id, @RequestBody final SkillsDto skillsDto) {
        service.putSkill(id, skillsDto);
        return ResponseEntity.status(HttpStatus.OK).body("Skill updated successfully");
    }

    /**
     * Suppression d'un skills
     * @param id l'identifiant
     */
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable final String id){
        service.deleteSkill(id);
        return ResponseEntity.status(HttpStatus.OK).body("Skill deleted successfully");
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> fetchUser(@PathVariable final String id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/users")
    public ResponseEntity<Set<UserDto>> fetchUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody final UserDto userDto){
        userService.postUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable final String id, @RequestBody final UserDto userDto){
        userService.putUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body("Utilisateur mis a jour avec succès");
    }

    /**
     * Suppression d'un utilisateur
     * @param id l'identifiant de l'utilisateur
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> dropUser(@PathVariable final String id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Utilisateur Supprimé avec Succès");
    }
    
    @GetMapping("/freelance-profiles/{id}")
    public ResponseEntity<FreelanceProfileDto> fetchFreelanceProfile(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getFreelanceProfile(id));
    }

    @GetMapping("/freelance-profiles")
    public ResponseEntity<List<FreelanceProfileDto>> fetchFreelanceProfiles(){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getAllFreelanceProfiles());
    }

    @PostMapping("/freelance-profiles")
    public ResponseEntity<String> createFreelanceProfile(@RequestBody final FreelanceProfileDto freelance){
        freelanceService.PostFreelanceProfile(freelance);
        return ResponseEntity.status(HttpStatus.CREATED).body("Freelance créé avec succès");
    }

    @PutMapping("/freelance-profiles/{id}")
    public ResponseEntity<String> updateFreelanceProfile(@PathVariable final String id, @RequestBody final FreelanceProfileDto freelance){
        freelanceService.PutFreelanceProfile(id, freelance);
        return ResponseEntity.status(HttpStatus.OK).body("freelance modifié avec succès.");
    }

    @DeleteMapping("/freelance-profiles/{id}")
    public ResponseEntity<String> dropFreelanceProfile(@PathVariable final String id){
        freelanceService.DeleteFreelanceProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body("Freelance Supprimé avec succès.");
    }

    @GetMapping("/freelance-experiences/{id}")
    public ResponseEntity<FreelanceExperienceDto> fetchFreelanceExperience(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getFreelanceExperience(id));
    }

    @GetMapping("/freelance-experiences")
    public ResponseEntity<List<FreelanceExperienceDto>> fetchFreelanceExperiences(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getAllFreelanceExperiencesByProfileId(id));
    }

    @PostMapping("/freelance-experiences")
    public ResponseEntity<String> createFreelanceExperience(@RequestBody final FreelanceExperienceDto experience){
        freelanceService.PostFreelanceExperience(experience);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expérience créé avec succès.");
    }

    @PutMapping("/freelance-experiences/{id}")
    public ResponseEntity<String> updateFreelanceExperience(@PathVariable final String id, @RequestBody final FreelanceExperienceDto experience){
        freelanceService.PutFreelanceExperience(id, experience);
        return ResponseEntity.status(HttpStatus.OK).body("Expérience modifié avec succès.");
    }

    @DeleteMapping("/freelance-experiences/{id}")
    public ResponseEntity<String> dropFreelanceExperience(@PathVariable final String id){
        freelanceService.DeleteFreelanceExperience(id);
        return ResponseEntity.status(HttpStatus.OK).body("Expérience Supprimé avec succès.");
    }
    
    @GetMapping("/freelance-skills/{id}")
    public ResponseEntity<FreelanceSkillsDto> fetchFreelanceSkills(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getFreelanceSkills(id));
    }

    public ResponseEntity<Set<FreelanceSkillsDto>> fetchFreelanceSkillsByProfileId(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getAllFreelanceSkillsByProfileId(id));
    }

    @PostMapping("/freelance-skills")
    public ResponseEntity<String> createFreelanceSkills(@RequestBody final FreelanceSkillsDto freelanceSkills){
        freelanceService.PostFreelanceSkills(freelanceSkills);
        return ResponseEntity.status(HttpStatus.CREATED).body("Compétences du freelance créé avec succès.");
    }
    @PutMapping("/freelance-skills/{id}")
    public ResponseEntity<String> updateFreelanceSkills(@PathVariable final String id, @RequestBody final FreelanceSkillsDto freelanceSkills){
        freelanceService.PutFreelanceSkills(id, freelanceSkills);
        return ResponseEntity.status(HttpStatus.OK).body("Compétences du freelance modifié avec succès.");
    }

    @DeleteMapping("/freelance-skills/{id}")
    public ResponseEntity<String> dropFreelanceSkills(@PathVariable final String id){
        freelanceService.DeleteFreelanceSkills(id);
        return ResponseEntity.status(HttpStatus.OK).body("Compétences du freelance Supprimé avec succès.");
    }

    @GetMapping("/ratings/{id}")
    public ResponseEntity<RatingDto> fetchRating(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getRating(id));
    }
    
    @GetMapping("/ratings/profile/{id}")
     public ResponseEntity<Set<RatingDto>> fetchRatingsByProfileId(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(freelanceService.getAllRatingsByProfileId(id));
    }
    @PostMapping("/ratings")
    public ResponseEntity<String> createRating(@RequestBody final RatingDto rating){
        freelanceService.PostRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body("Note créé avec succès.");
    }
    @PutMapping("/ratings/{id}")
    public ResponseEntity<String> updateRating(@PathVariable final String id, @RequestBody final RatingDto rating){
        freelanceService.PutRating(id, rating);
        return ResponseEntity.status(HttpStatus.OK).body("Note modifié avec succès.");
    }
    @DeleteMapping("/ratings/{id}")
    public ResponseEntity<String> dropRating(@PathVariable final String id){
        freelanceService.DeleteRating(id);
        return ResponseEntity.status(HttpStatus.OK).body("Note Supprimé avec succès.");
    }
    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<SubscriptionDto> fetchSubscription(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getSubscription(id));
    }
    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> fetchSubscriptions(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getSubscriptions());
    }
    
    @PostMapping("/subscriptions")
    public ResponseEntity<String> createSubscription(@RequestBody final SubscriptionDto subscription){
        service.postSubscription(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body("Abonnement créé avec succès.");
    }
    @PutMapping("/subscriptions/{id}")
    public ResponseEntity<String> updateSubscription(@PathVariable final String id, @RequestBody final SubscriptionDto subscription){
        service.PutSubscription(id, subscription);
        return ResponseEntity.status(HttpStatus.OK).body("Abonnement modifié avec succès.");
    }
    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity<String> dropSubscription(@PathVariable final String id){
        service.deleteSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).body("Abonnement Supprimé avec succès.");
    }

    @GetMapping("/missions/{id}")
    public ResponseEntity<MissionDto> fetchMission(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getMission(id));
    }

    @GetMapping("/missions")
    public ResponseEntity<List<MissionDto>> fetchMissions(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getMissions());
    }

    @PostMapping("/missions")
    public ResponseEntity<String> createMission(@RequestBody final MissionDto mission){
        service.postMission(mission);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mission créé avec succès.");
    }
    @PutMapping("/missions/{id}")
    public ResponseEntity<String> updateMission(@PathVariable final String id, @RequestBody final MissionDto mission){
        service.PutMission(id, mission);
        return ResponseEntity.status(HttpStatus.OK).body("Mission modifié avec succès.");  
    }
    @DeleteMapping("/missions/{id}")
    public ResponseEntity<String> dropMission(@PathVariable final String id){
        service.deleteMission(id);
        return ResponseEntity.status(HttpStatus.OK).body("Mission Supprimé avec succès.");
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentDto> fetchPayment(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPayment(id));
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDto>> fetchPayments(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPayments());
    }

    @PostMapping("/payments")
    public ResponseEntity<String> createPayment(@RequestBody final PaymentDto payment){
        service.postPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paiement créé avec succès.");
    }
    @PutMapping("/payments/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable final String id, @RequestBody final PaymentDto payment){
        service.PutPayment(id, payment);
        return ResponseEntity.status(HttpStatus.OK).body("Paiement modifié avec succès.");
    }
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<String> dropPayment(@PathVariable final String id){
        service.deletePayment(id);
        return ResponseEntity.status(HttpStatus.OK).body("Paiement Supprimé avec succès.");
    }
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewDto> fetchReview(@PathVariable final String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getReview(id));
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> fetchReviews(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getReviews());
    }
    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@RequestBody final ReviewDto review){
        service.postReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body("Review créé avec succès.");
    }
    @PutMapping("/reviews/{id}")
    public ResponseEntity<String> updateReview(@PathVariable final String id, @RequestBody final ReviewDto review){
        service.PutReview(id, review);
        return ResponseEntity.status(HttpStatus.OK).body("Review modifié avec succès.");
    }
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> dropReview(@PathVariable final String id){
        service.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK).body("Review Supprimé avec succès.");
    }

}
