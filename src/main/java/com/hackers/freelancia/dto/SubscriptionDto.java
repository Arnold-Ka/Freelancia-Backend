package com.hackers.freelancia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    private String id;
    private String userId;
    private String type;// A changer le String par un enum
    private String startDate;
    private String endDate;
    private String status;
}
