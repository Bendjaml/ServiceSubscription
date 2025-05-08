package com.example.ServiceSubscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDto {
    private Long id;
    private String serviceName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;
}
