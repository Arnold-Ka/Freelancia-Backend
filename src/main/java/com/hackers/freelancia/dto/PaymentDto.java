package com.hackers.freelancia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private String id;
    private String userId;
    private String missionId;
    private double amount;
    private Double commission;
    private String status;
}
