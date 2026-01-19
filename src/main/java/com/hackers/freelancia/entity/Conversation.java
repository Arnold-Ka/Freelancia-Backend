package com.hackers.freelancia.entity;

import java.util.List;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "conversations")
@Entity
@Getter
@Setter
public class Conversation extends BaseEntity{
    private String name;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;
}
