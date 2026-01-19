package com.hackers.freelancia.entity;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "messages")
@Entity
@Getter
@Setter
public class Message  extends BaseEntity{
    private String content;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Conversation conversation;
}



