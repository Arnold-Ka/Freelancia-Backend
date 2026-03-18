package com.hackers.freelancia.controller;

import java.util.List;
import java.util.Set;

import com.hackers.freelancia.config.ApiResponse;
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

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
public class freelanciaController  {
    private final FreelanciaService service;
    private final UserService userService;
    private final FreelanceProfileService freelanceService;


    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> fetchCategory(@PathVariable final String id) throws NotFoundException{
        return ResponseEntity.ok(ApiResponse.success(service.getCategory(id)));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> fetchCategories() throws NotFoundException {
        return  ResponseEntity.ok(ApiResponse.success(service.getCategories()));
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<String>> createCategory(@RequestBody final CategoryDto categoryDto) throws NotFoundException{
        service.postCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Category enregistrer avec succès"));
    }
    
    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<String>> updateCategory(@PathVariable final String id, @RequestBody final CategoryDto categoryDto)
    throws NotFoundException {
        service.putCategory(id, categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Categorie mis a jour avec succès"));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<String>> dropCategory(@PathVariable final String id) throws NotFoundException {
        service.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Category Sypprimer avec Succès"));
    }
    
    /**
     * Récuperations des skills.
     * @return la liste de skills
     */
    @GetMapping("/skills")
    public ResponseEntity<ApiResponse<List<SkillsDto>>> getAllSkills() throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getSkills()));
    }
    /**
     * Recupéré un seul skills grace a son identifiant.
     * @param id l'identifiant
     * @return le skills
     */
    @GetMapping("/skills/{id}")
    public ResponseEntity<ApiResponse<SkillsDto>> getSkillById(@PathVariable final String id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getSkill(id)));
    }

    /**
     * Création d'un skills
     * @param skillsDto les données sur le skill
     */
    @PostMapping("/skills")
    public ResponseEntity<ApiResponse<String>> createSkill(@RequestBody final SkillsDto skillsDto) throws NotFoundException {
        service.postSkill(skillsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Skill created successfully"));
    }

    /**
     * Modification des informations d'un skills
     * @param id l'identifiant
     * @param skillsDto les nouvelles données sur le skills
     */ 
    @PutMapping("/skills/{id}")
    public ResponseEntity<ApiResponse<String>> updateSkills(@PathVariable final String id, @RequestBody final SkillsDto skillsDto)
                                                    throws NotFoundException {
        service.putSkill(id, skillsDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Skill updated successfully"));
    }

    /**
     * Suppression d'un skills
     * @param id l'identifiant
     */
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSkill(@PathVariable final String id) throws NotFoundException{
        service.deleteSkill(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Skill deleted successfully"));
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> fetchUser(@PathVariable final String id) throws NotFoundException{
        return ResponseEntity.ok(ApiResponse.success(userService.getUser(id)));
    }

    @GetMapping("users/me/{username}")
    public ResponseEntity<ApiResponse<UserDto>> fetchUserInfos(@PathVariable final String username) throws Exception{
        return ResponseEntity.ok(ApiResponse.success(userService.getByUsername(username)));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Set<UserDto>>> fetchUsers() throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userService.getAllUsers()));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody final UserDto userDto) throws NotFoundException{
        userService.postUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Utilisateur créé avec succès"));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> updanullteUser(@PathVariable final String id, @RequestBody final UserDto userDto)
                                                    throws NotFoundException{
        userService.putUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Utilisateur mis a jour avec succès"));
    }

    /**
     * Suppression d'un utilisateur
     * @param id l'identifiant de l'utilisateur
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> dropUser(@PathVariable final String id) throws NotFoundException{
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Utilisateur Supprimé avec Succès"));
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
    public ResponseEntity<ApiResponse<SubscriptionDto>> fetchSubscription(@PathVariable final String id)
                                                throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getSubscription(id)));
    }
    @GetMapping("/subscriptions")
    public ResponseEntity<ApiResponse<List<SubscriptionDto>>> fetchSubscriptions() throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getSubscriptions()));
    }
    
    @PostMapping("/subscriptions")
    public ResponseEntity<String> createSubscription(@RequestBody final SubscriptionDto subscription) throws NotFoundException{
        service.postSubscription(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body("Abonnement créé avec succès.");
    }
    @PutMapping("/subscriptions/{id}")
    public ResponseEntity<ApiResponse<String>> updateSubscription(@PathVariable final String id, @RequestBody final SubscriptionDto subscription)
                                    throws NotFoundException {
        service.PutSubscription(id, subscription);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Abonnement modifié avec succès."));
    }
    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity<ApiResponse<String>> dropSubscription(@PathVariable final String id) throws NotFoundException{
        service.deleteSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Abonnement Supprimé avec succès."));
    }

    @GetMapping("/missions/{id}")
    public ResponseEntity<ApiResponse<MissionDto>> fetchMission(@PathVariable final String id) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getMission(id)));
    }

    @GetMapping("/missions")
    public ResponseEntity<ApiResponse<List<MissionDto>>> fetchMissions() throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getMissions()));
    }

    @PostMapping("/missions")
    public ResponseEntity<ApiResponse<String>> createMission(@RequestBody final MissionDto mission)throws NotFoundException{
        service.postMission(mission);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Mission créé avec succès."));
    }
    @PutMapping("/missions/{id}")
    public ResponseEntity<ApiResponse<String>> updateMission(@PathVariable final String id, @RequestBody final MissionDto mission) throws NotFoundException{
        service.PutMission(id, mission);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Mission modifié avec succès."));  
    }
    @DeleteMapping("/missions/{id}")
    public ResponseEntity<ApiResponse<String>> dropMission(@PathVariable final String id) throws NotFoundException{
        service.deleteMission(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Mission Supprimé avec succès."));
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<ApiResponse<PaymentDto>> fetchPayment(@PathVariable final String id) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getPayment(id)));
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> fetchPayments() throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getPayments()));
    }

    @PostMapping("/payments")
    public ResponseEntity<ApiResponse<String>> createPayment(@RequestBody final PaymentDto payment) throws NotFoundException{
        service.postPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Paiement créé avec succès."));
    }
    @PutMapping("/payments/{id}")
    public ResponseEntity<ApiResponse<String>> updatePayment(@PathVariable final String id, @RequestBody final PaymentDto payment)
                                                    throws NotFoundException{
        service.PutPayment(id, payment);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Paiement modifié avec succès."));
    }
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<ApiResponse<String>> dropPayment(@PathVariable final String id) throws NotFoundException{
        service.deletePayment(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Paiement Supprimé avec succès."));
    }
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<ReviewDto>> fetchReview(@PathVariable final String id) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getReview(id)));
    }

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> fetchReviews() throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(service.getReviews()));
    }
    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<String>> createReview(@RequestBody final ReviewDto review) throws NotFoundException{
        service.postReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Review créé avec succès."));
    }
    @PutMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<String>> updateReview(@PathVariable final String id, @RequestBody final ReviewDto review) throws NotFoundException{
        service.PutReview(id, review);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Review modifié avec succès."));
    }
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<String>> dropReview(@PathVariable final String id) throws NotFoundException{
        service.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Review Supprimé avec succès."));
    }

}
