package com.hackers.freelancia.repository;

import java.util.List;

import com.hackers.freelancia.config.AbstractRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.entity.Message;

public interface MessageRepository extends AbstractRepository<Message, String> {

    List<Message> findByConversationIdAndStatut(String conversationId, Statut active);
}

