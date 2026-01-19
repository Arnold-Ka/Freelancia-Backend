package com.hackers.freelancia.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissionDto {

    private String id;
    private String title;
    private String description;
    private String statuMission;
    private String clientId;
    private Double price;
    private String freelanceProfileId;
    private Set<String> skillsId;
    private String deadline;
}
