package com.hackers.freelancia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hackers.freelancia.dto.UserDto;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;


    public List<UserDto> getUsers(){
        return userRepository.findAllActive().stream().map(mapper::maps).toList();
    }
}
