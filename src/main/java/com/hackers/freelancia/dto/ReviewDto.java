package com.hackers.freelancia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private String id;
    private String missionId;
    private String userId;
    private String comment;
    private int rating;
}
