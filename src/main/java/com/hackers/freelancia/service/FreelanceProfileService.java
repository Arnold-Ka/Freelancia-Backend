package com.hackers.freelancia.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.config.Utils;
import com.hackers.freelancia.dto.FreelanceExperienceDto;
import com.hackers.freelancia.dto.FreelanceProfileDto;
import com.hackers.freelancia.dto.FreelanceSkillsDto;
import com.hackers.freelancia.dto.RatingDto;
import com.hackers.freelancia.entity.FreelanceExperience;
import com.hackers.freelancia.entity.FreelanceProfile;
import com.hackers.freelancia.entity.FreelanceSkills;
import com.hackers.freelancia.entity.Rating;
import com.hackers.freelancia.entity.User;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.repository.FreelanceExperienceRepository;
import com.hackers.freelancia.repository.FreelanceProfileRepository;
import com.hackers.freelancia.repository.FreelanceSkillsRepository;
import com.hackers.freelancia.repository.RatingRepository;
import com.hackers.freelancia.repository.SkillsRepository;
import com.hackers.freelancia.repository.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FreelanceProfileService {
    private final FreelanceProfileRepository freelanceProfileRepository;
    private final FreelanceExperienceRepository freelanceExperienceRepository;
    private final FreelanceSkillsRepository freelanceSkillsRepository;
    private final SkillsRepository skillsRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    /**
     * Récupère un profil de freelance par son ID.
     * 
     * @param id l'identifiant du profil de freelance
     * @return le profil de freelance correspondant à l'ID fourni
     */
    public FreelanceProfileDto getFreelanceProfile(@NonNull final String id) {

        Set<Rating> ratings = ratingRepository.findByFreelanceProfileId(id); // Récupération des notes
        long rating = ratings.stream().mapToLong(Rating::getRatingValue).sum() / ratings.size(); // Calcul de la note
                                                                                                 // moyenne
        FreelanceProfile freelanceProfile = freelanceProfileRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Freelance profile not found with id: " + id));
        FreelanceProfileDto freelanceProfileDto = mapper.maps(freelanceProfile);
        freelanceProfileDto.setRating(rating);
        return freelanceProfileDto;

    }

    /**
     * Récupère tous les profils de freelance actifs.
     * 
     * @return une liste de tous les profils de freelance actifs
     */
    public List<FreelanceProfileDto> getAllFreelanceProfiles() {
        List<FreelanceProfile> freelanceProfiles = freelanceProfileRepository.findAllActive();
        List<FreelanceProfileDto> freelanceProfileDtos = new java.util.ArrayList<>();
        // Boucle sur les profils de freelance pour calculer la note moyenne et les
        // convertir en DTO
        
        for (FreelanceProfile freelanceProfile : freelanceProfiles) {
            Set<Rating> ratings = ratingRepository.findByFreelanceProfileId(freelanceProfile.getId()); // Récupération
                                                                                                       // des notes
            long rating = 0;
            if (ratings.size() != 0) {
                rating = ratings.stream().mapToLong(Rating::getRatingValue).sum() / ratings.size();
            }
             // Calcul de la
                                                                                                     // note
                                                                                                     // moyenne
            FreelanceProfileDto freelanceProfileDto = mapper.maps(freelanceProfile);
            freelanceProfileDto.setRating(rating);
            freelanceProfileDtos.add(freelanceProfileDto);
        }
        return freelanceProfileDtos;
    }

    /**
     * Crée un nouveau profil de freelance.
     * 
     * @param freelanceProfileDto les informations du nouveau profil de freelance
     * @param freelanceProfileDto
     */
    public void PostFreelanceProfile(final FreelanceProfileDto freelanceProfileDto) {
        if (freelanceProfileDto.getUserId() == null || freelanceProfileDto.getUserId() == "" || !userRepository.existsById(freelanceProfileDto.getUserId())) {
            throw new IllegalArgumentException("L'id de l'utilisateur est invalide");
        }
        User user = userRepository.findByIdAndStatut(freelanceProfileDto.getUserId(), Statut.ACTIVE).orElseThrow(() -> new IllegalArgumentException());
        user.setFreelance(true);
        FreelanceProfile freelanceProfile = mapper.maps(freelanceProfileDto);
        freelanceProfile.setId(Utils.generateId());
        freelanceProfileRepository.save(freelanceProfile);
    }

    /**
     * Met à jour un profil de freelance existant.
     * 
     * @param id                  l'identifiant du profil de freelance à mettre à
     *                            jour
     * @param freelanceProfileDto les nouvelles informations du profil de freelance
     */
    public void PutFreelanceProfile(final String id, final FreelanceProfileDto freelanceProfileDto) {
        if (!freelanceProfileRepository.existsById(id)) {
            throw new RuntimeException("Freelance profile not found with id: " + id);
        }
        FreelanceProfile freelanceProfile = mapper.maps(freelanceProfileDto);
        freelanceProfile.setId(id);
        freelanceProfileRepository.save(freelanceProfile);
    }

    /**
     * Supprime un profil de freelance en le marquant comme supprimé.
     * 
     * @param id l'identifiant du profil de freelance à supprimer
     */
    public void DeleteFreelanceProfile(@NonNull final String id) {
        FreelanceProfile freelanceProfile = freelanceProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freelance profile not found with id: " + id));
        freelanceProfile.setStatut(Statut.DELETED);
        freelanceProfileRepository.save(freelanceProfile);
    }

    /**
     * Récupère une expérience de freelance par son ID.
     * 
     * @param id l'identifiant de l'expérience de freelance
     * @return l'expérience de freelance correspondante à l'ID fourni
     */
    public FreelanceExperienceDto getFreelanceExperience(@NonNull final String id) {
        FreelanceExperience freelanceExperience = freelanceExperienceRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Freelance experience not found with id: " + id));
        FreelanceExperienceDto freelanceExperienceDto = mapper.maps(freelanceExperience);
        return freelanceExperienceDto;
    }

    /**
     * Récupère toutes les expériences de freelance associées à un profil de
     * freelance donné.
     * 
     * @param profileId l'identifiant du profil de freelance
     * @return une liste de toutes les expériences de freelance associées au profil
     *         de freelance spécifié
     */
    public List<FreelanceExperienceDto> getAllFreelanceExperiencesByProfileId(@NonNull final String profileId) {
        List<FreelanceExperience> freelanceExperiences = freelanceExperienceRepository
                .findByFreelanceProfileId(profileId);
        return freelanceExperiences.stream().map(mapper::maps).toList();
    }

    /**
     * Crée une nouvelle expérience de freelance.
     * 
     * @param freelanceExperienceDto les informations de la nouvelle expérience de
     *                               freelance
     */
    public void PostFreelanceExperience(final FreelanceExperienceDto freelanceExperienceDto) {
        FreelanceExperience freelanceExperience = mapper.maps(freelanceExperienceDto);
        freelanceExperience.setId(Utils.generateId());
        freelanceExperienceRepository.save(freelanceExperience);
    }

    /**
     * Met à jour une expérience de freelance existante.
     * 
     * @param id                     l'identifiant de l'expérience de freelance à
     *                               mettre à jour
     * @param freelanceExperienceDto les nouvelles informations de l'expérience de
     *                               freelance
     */
    public void PutFreelanceExperience(final String id, final FreelanceExperienceDto freelanceExperienceDto) {
        FreelanceExperience freelanceExperience = mapper.maps(freelanceExperienceDto);
        freelanceExperience.setId(id);
        freelanceExperienceRepository.save(freelanceExperience);
    }

    /**
     * Supprime une expérience de freelance en la marquant comme supprimée.
     * 
     * @param id l'identifiant de l'expérience de freelance à supprimer
     */
    public void DeleteFreelanceExperience(@NonNull final String id) {
        FreelanceExperience freelanceExperience = freelanceExperienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freelance experience not found with id: " + id));
        freelanceExperience.setStatut(Statut.DELETED);
        freelanceExperienceRepository.save(freelanceExperience);
    }

    /**
     * Récupère une note par son ID.
     * 
     * @param id l'identifiant de la note
     * @return la note correspondante à l'ID fourni
     */
    public RatingDto getRating(@NonNull final String id) {
        Rating rating = ratingRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + id));
        return mapper.maps(rating);
    }

    /**
     * Récupère toutes les notes associées à un profil de freelance donné.
     * 
     * @param profileId l'identifiant du profil de freelance
     * @return une liste de toutes les notes associées au profil de freelance
     *         spécifié
     */
    public Set<RatingDto> getAllRatingsByProfileId(@NonNull final String profileId) {
        Set<Rating> ratings = ratingRepository.findByFreelanceProfileId(profileId);
        return ratings.stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Crée une nouvelle note.
     * 
     * @param ratingDto les informations de la nouvelle note
     */
    public void PostRating(final RatingDto ratingDto) {
        Rating rating = mapper.maps(ratingDto);
        rating.setId(Utils.generateId());
        ratingRepository.save(rating);
    }

    /**
     * Met à jour une note existante.
     * 
     * @param id        l'identifiant de la note à mettre à jour
     * @param ratingDto les nouvelles informations de la note
     */
    public void PutRating(final String id, final RatingDto ratingDto) {
        Rating rating = mapper.maps(ratingDto);
        rating.setId(id);
        ratingRepository.save(rating);
    }

    /**
     * Supprime une note en la marquant comme supprimée.
     * 
     * @param id l'identifiant de la note à supprimer
     */
    public void DeleteRating(@NonNull final String id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + id));
        rating.setStatut(Statut.DELETED);
        ratingRepository.save(rating);
    }

    /**
     * Récupère une expérience de freelance par son ID.
     * 
     *
     * @param id l'identifiant de l'expérience de freelance
     * @return l'expérience de freelance correspondante à l'ID fourni
     */
    public FreelanceSkillsDto getFreelanceSkills(@NonNull final String id) {
        FreelanceSkills freelanceSkills = freelanceSkillsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freelance skills not found with id: " + id));
        return mapper.maps(freelanceSkills);
    }

    /**
     * Récupère une compétence de freelance par son ID de compétence.
     * 
     * @param skillsID l'identifiant de la compétence de freelance
     * @return la compétence de freelance correspondante à l'ID de compétence fourni
     */
    public Set<FreelanceSkillsDto> getFreelanceSkillsBySkills(@NonNull final String skillsID) {
        Set<FreelanceSkills> freelanceSkills = freelanceSkillsRepository.findBySkillsId(skillsID);
        return freelanceSkills.stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Récupère toutes les compétences de freelance associées à un profil de
     * freelance donné.
     * 
     * @param profileId l'identifiant du profil de freelance
     * @return une liste de toutes les compétences de freelance associées au profil
     *         de freelance spécifié
     */
    public Set<FreelanceSkillsDto> getAllFreelanceSkillsByProfileId(@NonNull final String profileId) {
        Set<FreelanceSkills> freelanceSkills = freelanceSkillsRepository.findByFreelanceProfileId(profileId);
        return freelanceSkills.stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Crée une nouvelle compétence de freelance.
     * 
     * @param freelanceSkillsDto les informations de la nouvelle compétence de
     *                           freelance
     */
    public void PostFreelanceSkills(final FreelanceSkillsDto freelanceSkillsDto) {
        FreelanceSkills freelanceSkills = mapper.maps(freelanceSkillsDto);
        freelanceSkills.setId(Utils.generateId());
        freelanceSkillsRepository.save(freelanceSkills);
    }

    /**
     * Met à jour une compétence de freelance existante.
     * 
     * @param id                 l'identifiant de la compétence de freelance à
     *                           mettre à jour
     * @param freelanceSkillsDto les nouvelles informations de la compétence de
     *                           freelance
     */
    public void PutFreelanceSkills(final String id, final FreelanceSkillsDto freelanceSkillsDto) {
        FreelanceSkills freelanceSkills = mapper.maps(freelanceSkillsDto);
        if (!freelanceSkillsRepository.existsById(id)) {
            throw new RuntimeException("Freelance skills not found with id: " + id);
        }
        if (!freelanceProfileRepository.existsById(freelanceSkillsDto.getFreelanceProfileId())) {
            throw new RuntimeException("Freelance profile not found with id: " + freelanceSkillsDto.getFreelanceProfileId());
        }
        if (!skillsRepository.existsById(freelanceSkillsDto.getSkillsId())) {
            throw new RuntimeException("Skills not found with id: " + freelanceSkillsDto.getSkillsId());
        }
        freelanceSkills.setId(id);
        freelanceSkillsRepository.save(freelanceSkills);
    }

    /**
     * Supprime une compétence de freelance en la marquant comme supprimée.
     * 
     * @param id l'identifiant de la compétence de freelance à supprimer
     */
    public void DeleteFreelanceSkills(@NonNull final String id) {
        FreelanceSkills freelanceSkills = freelanceSkillsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Freelance skills not found with id: " + id));
        freelanceSkills.setStatut(Statut.DELETED);
        freelanceSkillsRepository.save(freelanceSkills);
    }

}
