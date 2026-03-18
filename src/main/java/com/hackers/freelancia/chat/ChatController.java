package com.hackers.freelancia.chat;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService service;
    

}
