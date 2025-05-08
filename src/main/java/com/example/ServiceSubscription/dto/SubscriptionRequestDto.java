package com.example.ServiceSubscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDto {
    @NotBlank(message = "Service name is mandatory")
    private String serviceName;

    @Positive(message = "Monthly fee must be positive")
    private Double monthlyFee;
}
