package com.hackers.freelancia.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.config.Utils;
import com.hackers.freelancia.dto.ConversationDto;
import com.hackers.freelancia.dto.MessageDto;
import com.hackers.freelancia.entity.Conversation;
import com.hackers.freelancia.entity.Message;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.repository.ConversationRepository;
import com.hackers.freelancia.repository.MessageRepository;
import com.hackers.freelancia.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    /**
     * Récupère une conversation par son ID.
     *
     * @param id l'ID de la conversation
     * @return le DTO de la conversation
     * @throws RuntimeException si la conversation n'est pas trouvée ou n'est pas
     *                          active
     */
    public ConversationDto getConversation(final String id) {
        return mapper.maps(conversationRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation non Trouvée")));
    }

    /**
     * Récupère toutes les conversations d'un utilisateur.
     *
     * @param userId l'ID de l'utilisateur
     * @return la liste des DTO de conversation de l'utilisateur
     */
    public List<ConversationDto> getUserConversations(final String userId) {
        return conversationRepository.findBySenderIdOrReceiverIdAndStatut(userId, userId, Statut.ACTIVE)
                .stream()
                .map(mapper::maps)
                .collect(Collectors.toList());
    }

    /**
     * Crée une nouvelle conversation entre deux utilisateurs.
     *
     * @param conversationDto le DTO de la conversation à créer
     * @throws ResponseStatusException si l'un des utilisateurs n'est pas trouvé ou
     *                                 si les deux utilisateurs sont les mêmes
     */
    public void PostConversation(ConversationDto conversationDto) {
        if (!userRepository.existsById(conversationDto.getSenderId())
                || !userRepository.existsById(conversationDto.getReceiverId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non Trouvé");
        }
        if (conversationDto.getSenderId().equals(conversationDto.getReceiverId())) {
            throw new IllegalArgumentException("Utilisateur non Trouvé");
        }
        if (conversationRepository.existsBySenderIdAndReceiverIdAndStatut(conversationDto.getSenderId(),
                conversationDto.getReceiverId(), Statut.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conversation déjà existante");
        }
        conversationDto.setId(Utils.generateId());
        conversationRepository.save(mapper.maps(conversationDto));
    }

    /**
     * Met à jour une conversation existante.
     *
     * @param conversationDto le DTO de la conversation à mettre à jour
     */
    public void PutConversation(ConversationDto conversationDto) {
        ConversationDto existingConversation = getConversation(conversationDto.getId());
        if (existingConversation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation non Trouvée");
        }
        conversationRepository.save(mapper.maps(conversationDto));
    }

    /**
     * Supprime une conversation par son ID.
     *
     * @param id l'ID de la conversation à supprimer
     * @throws ResponseStatusException si la conversation n'est pas trouvée
     */
    public void DeleteConversation(String id) {
        Conversation existingConversation = conversationRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation non Trouvée"));
        existingConversation.setStatut(Statut.DELETED);
        conversationRepository.save(existingConversation);
    }

    /**
     * Récupère toutes les conversations actives.
     *
     * @return la liste des DTO de toutes les conversations actives
     */
    public List<ConversationDto> getAllConversations() {
        return conversationRepository.findAllActive()
                .stream()
                .map(mapper::maps)
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les messages d'une conversation.
     *
     * @param conversationId l'ID de la conversation
     * @return la liste des DTO de message de la conversation
     * @throws ResponseStatusException si la conversation n'est pas trouvée ou n'est
     *                                 pas active
     */
    public List<MessageDto> getConversationMessages(String conversationId) {
        if (!conversationRepository.existsByIdAndStatut(conversationId, Statut.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation non Trouvée");
        }
        return messageRepository.findByConversationIdAndStatut(conversationId, Statut.ACTIVE)
                .stream()
                .map(mapper::maps)
                .collect(Collectors.toList());
    }
    /**
     * Récupère un message par son ID.
     *
     * @param id l'ID du message
     * @throws ResponseStatusException si le message n'est pas trouvé ou n'est pas
     *                                 actif
     * @return le DTO du message
     */
    public MessageDto getMessage(String id) {
        return mapper.maps(messageRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message non Trouvé")));
    }

    /**
     * Envoie un message dans une conversation.
     *
     * @param messageDto le DTO du message à envoyer
     * @throws ResponseStatusException si la conversation n'est pas trouvée ou n'est
     *                                 pas active
     */
    public void PostMessage(MessageDto messageDto) {
        if (!conversationRepository.existsByIdAndStatut(messageDto.getConversationId(), Statut.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation non Trouvée");
        }
        messageDto.setId(Utils.generateId());
        messageRepository.save(mapper.maps(messageDto));
    }

    /**
     * Met à jour un message existant.
     *
     * @param id         l'ID du message à mettre à jour
     * @param messageDto le DTO du message avec les nouvelles données
     * @throws ResponseStatusException si le message n'est pas trouvé ou n'est pas
     *                                 actif
     */
    public void PutMessage(String id, MessageDto messageDto) {
        Message existingMessage = messageRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message non Trouvé"));

        existingMessage.setContent(messageDto.getContent());
        messageRepository.save(existingMessage);
    }

    /**
     * Supprime un message par son ID.
     *
     * @param id l'ID du message à supprimer
     * @throws ResponseStatusException si le message n'est pas trouvé ou n'est pas
     *                                 actif
     */
    public void DeleteMessage(String id) {
        Message existingMessage = messageRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message non Trouvé"));
        existingMessage.setStatut(Statut.DELETED);
        messageRepository.save(existingMessage);
    }

}
