package com.example.ServiceSubscription.service;

import com.example.ServiceSubscription.dto.SubscriptionRequestDto;
import com.example.ServiceSubscription.dto.SubscriptionResponseDto;

import java.util.List;

public interface SubscriptionService {
    SubscriptionResponseDto addSubscription(Long userId, SubscriptionRequestDto subscriptionRequestDto);
    List<SubscriptionResponseDto> getUserSubscriptions(Long userId);
    void deleteSubscription(Long userId, Long subscriptionId);
    List<SubscriptionResponseDto> getTop3PopularSubscriptions();
}