package com.hackers.freelancia.repository;

import java.util.List;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.Conversation;

public interface ConversationRepository extends AbstractRepository<Conversation, String> {

    List<Conversation> findBySenderIdOrReceiverIdAndStatut(String userId, String userId2, Statut active);

    boolean existsBySenderIdAndReceiverIdAndStatut(String senderId, String receiverId, Statut active);
}

