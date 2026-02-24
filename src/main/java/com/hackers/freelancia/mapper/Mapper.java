package com.hackers.freelancia.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.hackers.freelancia.dto.CategoryDto;
import com.hackers.freelancia.dto.ConversationDto;
import com.hackers.freelancia.dto.FreelanceExperienceDto;
import com.hackers.freelancia.dto.FreelanceProfileDto;
import com.hackers.freelancia.dto.FreelanceSkillsDto;
import com.hackers.freelancia.dto.MessageDto;
import com.hackers.freelancia.dto.MissionDto;
import com.hackers.freelancia.dto.PaymentDto;
import com.hackers.freelancia.dto.PermissionDto;
import com.hackers.freelancia.dto.RatingDto;
import com.hackers.freelancia.dto.ReviewDto;
import com.hackers.freelancia.dto.RoleDto;
import com.hackers.freelancia.dto.SkillsDto;
import com.hackers.freelancia.dto.SubscriptionDto;
import com.hackers.freelancia.dto.UserDto;
import com.hackers.freelancia.entity.Category;
import com.hackers.freelancia.entity.Conversation;
import com.hackers.freelancia.entity.FreelanceExperience;
import com.hackers.freelancia.entity.FreelanceProfile;
import com.hackers.freelancia.entity.FreelanceSkills;
import com.hackers.freelancia.entity.Message;
import com.hackers.freelancia.entity.Mission;
import com.hackers.freelancia.entity.Payment;
import com.hackers.freelancia.entity.Permission;
import com.hackers.freelancia.entity.Rating;
import com.hackers.freelancia.entity.Review;
import com.hackers.freelancia.entity.Role;
import com.hackers.freelancia.entity.Skills;
import com.hackers.freelancia.entity.Subscription;
import com.hackers.freelancia.entity.User;


/**
 * Mapper générale de l'application.
 *
 * @author : <A HREF="mailto:karambiriarnold@gmail.com">Karambiri Lawatan Arnold
 *         Bily</A>
 * @version : 1.0
 *          Copyright (c) 2021 All rights reserved.
 * @since : 14/05/2021 à 13:51
 */
@Component
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Mapper {

    /**
     * Mapper pour les catégories vers les dtos.
     * 
     * @param category la catégorie
     * @return le dto de la catégorie
     */
    CategoryDto maps(Category category);

    /**
     * Mapper du dto vers la catégorie.
     * 
     * @param categoryDto le dto de la catégorie
     * @return la catégorie ainsi retournée
     */
    @InheritInverseConfiguration
    Category maps(CategoryDto categoryDto);

    /**
     * Mapper pour les Skills vers les dto.
     * 
     * @param skills le skills
     * @return le Dto
     */
    @Mapping(target = "categoryId", source = "category.id")
    SkillsDto maps(Skills skills);

    /**
     * Mapper pour les skills Dto vers les Skills.
     * 
     * @param skillsDto le Dto
     * @return le Skills
     */
    @InheritInverseConfiguration
    Skills maps(SkillsDto skillsDto);

    /**
     * Mapper pour le freelances vers les dtos.
     * 
     * @param freelanceProfile le freelance
     * @return le dto du freelance
     */
    @Mapping(target = "userId", source = "user.id")
    FreelanceProfileDto maps(FreelanceProfile freelanceProfile);

    /**
     * Mapper pour le dto vers le freelance.
     * 
     * @param freelanceProfileDto le dto du freelance
     * @return le freelance
     */
    @InheritInverseConfiguration
    FreelanceProfile maps(FreelanceProfileDto freelanceProfileDto);

    /**
     * Mapper pour les expériences de freelance vers leurs dtos.
     * 
     * @param freelanceExperience l'expérience de freelance
     * @return le dto de cette expérience de freelance
     */
    @Mapping(target = "freelanceProfileId", source = "freelanceProfile.id")
    FreelanceExperienceDto maps(FreelanceExperience freelanceExperience);

    /**
     * Mapper du dto vers l'expérience de freelance.
     * 
     * @param freelanceExperienceDto le dto de l'expérience de freelance
     * @return l'expérience de freelance ainsi retournée
     */
    @InheritInverseConfiguration
    FreelanceExperience maps(FreelanceExperienceDto freelanceExperienceDto);

    /**
     * Mapper pour les missions vers leurs dtos.
     * 
     * @param Mission la mission
     * @return le dto de cette mission
     */
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "freelanceProfileId", source = "freelanceProfile.id")
    MissionDto maps(Mission Mission);

    /**
     * Mapper du dto vers la mission.
     * 
     * @param missionDto le dto
     * @return la mission ainsi retourné
     */
    @InheritInverseConfiguration
    Mission maps(MissionDto missionDto);

    /**
     * Mapper de conversation vers ses dtos.
     * 
     * @param conversation la conversation
     * @return le dto
     */
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "receiverId", source = "receiver.id")
    ConversationDto maps(Conversation conversation);

    @InheritInverseConfiguration
    /**
     * Mapper du dto vers une conversation.
     * 
     * @param conversationDto le dto
     * @return la conversation
     */
    Conversation maps(ConversationDto conversationDto);

    /**
     * Mapper de message vers ses dtos.
     * 
     * @param message le message
     * @return le dto
     */
    @Mapping(target = "conversationId", source = "conversation.id")
    @Mapping(target = "senderId", source = "sender.id")
    MessageDto maps(Message message);

    @InheritInverseConfiguration
    /**
     * Mapper du dto vers un message.
     * 
     * @param messageDto le dto
     * @return le message
     */
    Message maps(MessageDto messageDto);

    /**
     * Mapper le payment vers les dtos.
     * 
     * @param payment le payment
     * @return le dto
     */
    @Mapping(target = "missionId", source = "mission.id")
    PaymentDto maps(Payment payment);

    /**
     * Mapper du dto vers payment.
     * 
     * @param paymentDto le dto
     * @return le paymant
     */
    @InheritInverseConfiguration
    Payment maps(PaymentDto paymentDto);

    /**
     * Mapper de Freelance skills vers Dto.
     * 
     * @param freelanceSkills le freelance skills
     * @return le dto
     */
    @Mapping(target = "freelanceProfileId", source = "freelanceProfile.id")
    @Mapping(target = "skillsId", source = "skills.id")
    FreelanceSkillsDto maps(FreelanceSkills freelanceSkills);

    /**
     * Mapper de dto vers freelance skills.
     * 
     * @param freelanceSkillsDto le freelance skills dto
     * @return le freelance skills
     */
    @InheritInverseConfiguration
    FreelanceSkills maps(FreelanceSkillsDto freelanceSkillsDto);

    /**
     * Mapper de Rating vers Dto.
     * 
     * @param rating le rating
     * @return le dto
     */
    @Mapping(target = "freelanceProfileId", source = "freelanceProfile.id")
    RatingDto maps(Rating rating);

    /**
     * Mapper de Dto vers Rating.
     * 
     * @param ratingDto le dto
     * @return le rating
     */
    @InheritInverseConfiguration
    Rating maps(RatingDto ratingDto);

    /**
     * Mapper de review vers ses dto.
     * 
     * @param review la review
     * @return le dto
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "missionId", source = "mission.id")
    ReviewDto maps(Review review);

    /**
     * 
     * @param reviewDto
     * @return
     */
    @InheritInverseConfiguration
    Review maps(ReviewDto reviewDto);

    /**
     * Mapper pour subscription vers dto.
     * 
     * @param subscription la subscription
     * @return le dto
     */
    @Mapping(target = "freelanceProfileId", source = "freelanceProfile.id")
    SubscriptionDto maps(Subscription subscription);

    /**
     * Mapper de dto vers subscription.
     * 
     * @param subscriptionDto le dto
     * @return la subsciption
     */
    @InheritInverseConfiguration
    Subscription maps(SubscriptionDto subscriptionDto);

    /**
     * Mapper de User vers ses dto
     * 
     * @param user
     * @return
     */
    UserDto maps(User user);

    /**
     * Mapper de dto vers user
     * 
     * @param userDto le dto
     * @return le user
     */
    @InheritInverseConfiguration
    User maps(UserDto userDto);

    /**
     * Mapper de Role vers dto
     * 
     * @param role
     * @return
     */
    RoleDto maps(Role role);

    /**
     * Mapper de dto vers role
     * 
     * @param roleDto le dto
     * @return le role
     */
    @InheritInverseConfiguration
    Role maps(RoleDto roleDto);

    /**
     * Mapper Permission vers dto
     * 
     * @param permission la permission
     * @return le dto
     */
    PermissionDto maps(Permission permission);

    /**
     * Dto vers Permission
     * 
     * @param permissionDto le dto
     * @return la permission
     */
    @InheritInverseConfiguration
    Permission maps(PermissionDto permissionDto);
}
