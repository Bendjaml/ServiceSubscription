package com.example.ServiceSubscription.controller;

import com.example.ServiceSubscription.dto.SubscriptionRequestDto;
import com.example.ServiceSubscription.dto.SubscriptionResponseDto;
import com.example.ServiceSubscription.dto.TopSubscriptionResponseDto;
import com.example.ServiceSubscription.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<SubscriptionResponseDto> addSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionResponseDto subscription = subscriptionService.addSubscription(userId, subscriptionRequestDto);
        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<SubscriptionResponseDto>> getUserSubscriptions(
            @PathVariable Long userId) {
        List<SubscriptionResponseDto> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<TopSubscriptionResponseDto>> getTopSubscriptions() {
        List<TopSubscriptionResponseDto> topSubscriptions = subscriptionService.getTop3PopularSubscriptions();
        return ResponseEntity.ok(topSubscriptions);
    }
}