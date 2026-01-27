package com.hackers.freelancia.mapper;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.ReportingPolicy;

import com.hackers.freelancia.dto.ConversationDto;
import com.hackers.freelancia.dto.FreelanceProfileDto;
import com.hackers.freelancia.dto.FreelanceSkillsDto;
import com.hackers.freelancia.dto.MissionDto;
import com.hackers.freelancia.dto.PaymentDto;
import com.hackers.freelancia.dto.ReviewDto;
import com.hackers.freelancia.dto.SkillsDto;
import com.hackers.freelancia.dto.SubscriptionDto;
import com.hackers.freelancia.dto.UserDto;
import com.hackers.freelancia.entity.Conversation;
import com.hackers.freelancia.entity.FreelanceProfile;
import com.hackers.freelancia.entity.FreelanceSkills;
import com.hackers.freelancia.entity.Mission;
import com.hackers.freelancia.entity.Payment;
import com.hackers.freelancia.entity.Review;
import com.hackers.freelancia.entity.Skills;
import com.hackers.freelancia.entity.Subscription;
import com.hackers.freelancia.entity.User;

@org.mapstruct.Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface Mapper {

    /**
     * Mapper pour les Skills vers les dto.
     * @param skills le skills
     * @return le Dto
     */
    SkillsDto maps(Skills skills);

    /**
     * Mapper pour les skills Dto vers les Skills.
     * @param skillsDto le Dto
     * @return le Skills
     */
    @InheritInverseConfiguration
    Skills maps(SkillsDto skillsDto);

    /**
     * Mapper pour le freelances vers les dtos.
     * @param freelanceProfile le freelance
     * @return le dto du freelance
     */
    FreelanceProfileDto maps(FreelanceProfile freelanceProfile);
    /**
     * Mapper pour le dto vers le freelance.
     * @param freelanceProfileDto le dto du freelance
     * @return le freelance
     */
    @InheritInverseConfiguration
    FreelanceProfile maps(FreelanceProfileDto freelanceProfileDto);
   
    /**
     * Mapper pour les missions vers leurs dtos.
     * @param Mission la mission
     * @return le dto de cette mission
     */
    MissionDto maps(Mission Mission);

    /**
     * Mapper du dto vers la mission.
     * @param missionDto le dto
     * @return la mission ainsi retourné
     */
    @InheritInverseConfiguration
    Mission maps(MissionDto missionDto);

    /**
     * Mapper de conversation vers ses dtos.
     * @param conversation la conversation
     * @return le dto 
     */
    ConversationDto maps(Conversation conversation);
    @InheritInverseConfiguration
    /**
     * Mapper du dto vers une conversation.
     * @param conversationDto le dto
     * @return la conversation
     */
    Conversation maps(ConversationDto conversationDto);

    /**
     * Mapper le payment vers les dtos.
     * @param payment le payment
     * @return le dto
     */
    PaymentDto maps(Payment payment);
    /**
     * Mapper du dto vers payment.
     * @param paymentDto le dto
     * @return le paymant
     */
    @InheritInverseConfiguration
    Payment maps(PaymentDto paymentDto);

    /**
     * Mapper de Freelance skills vers Dto.
     * @param freelanceSkills le freelance skills
     * @return le dto
     */
    FreelanceSkillsDto maps(FreelanceSkills freelanceSkills);
    /**
     * Mapper de dto vers freelance skills.
     * @param freelanceSkillsDto le freelance skills dto
     * @return le freelance skills
     */
    @InheritInverseConfiguration
    FreelanceSkills maps(FreelanceSkillsDto freelanceSkillsDto);

    /**
     * Mapper de review vers ses dto.
     * @param review la review
     * @return le dto
     */
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
     * @param subscription la subscription
     * @return le dto
     */
    SubscriptionDto maps(Subscription subscription);

    /**
     * Mapper de dto vers subscription.
     * @param subscriptionDto le dto
     * @return la subsciption
     */
    @InheritInverseConfiguration
    Subscription maps(SubscriptionDto subscriptionDto);

    /**
     * Mapper de User vers ses dto
     * @param user
     * @return
     */
    UserDto maps(User user);
    /**
     * Mapper de dto vers user
     * @param userDto le dto
     * @return le user
     */
    @InheritInverseConfiguration
    User maps(UserDto userDto);
}
