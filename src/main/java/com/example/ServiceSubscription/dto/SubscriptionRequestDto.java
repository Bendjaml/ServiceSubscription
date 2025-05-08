package com.example.ServiceSubscription.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDto {
    @NotBlank(message = "Service name cannot be blank")
    @Size(max = 100,min=2, message = "Service name cannot be shorter than 2 and longer than 100 characters.")
    private String serviceName;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDateTime endDate;
}
