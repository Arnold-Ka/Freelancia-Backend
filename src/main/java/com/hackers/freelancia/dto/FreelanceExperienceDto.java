package com.hackers.freelancia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreelanceExperienceDto {
    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String freelanceProfileId;
    private String company;
    private String location;

}
