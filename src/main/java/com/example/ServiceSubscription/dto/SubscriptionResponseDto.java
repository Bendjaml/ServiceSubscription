package com.example.ServiceSubscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDto {
    private Long id;
    private String serviceName;
    private Double monthlyFee;
    private Long userId;
}
