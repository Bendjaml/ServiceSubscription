package com.example.ServiceSubscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
        private Long id;
        private String name;
        private String email;
        private List<SubscriptionResponseDto> subscriptions;
}
