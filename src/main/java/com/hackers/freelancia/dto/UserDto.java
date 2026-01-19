package com.hackers.freelancia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String profilePicture;
}
