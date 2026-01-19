package com.hackers.freelancia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreelanceSkillsDto {

    private String id;
    private String freelanceProfileId;
    private String skillsId;
    private double price;
}
