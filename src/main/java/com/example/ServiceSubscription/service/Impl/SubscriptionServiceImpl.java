package com.example.ServiceSubscription.service.Impl;

import com.example.ServiceSubscription.dto.SubscriptionRequestDto;
import com.example.ServiceSubscription.dto.SubscriptionResponseDto;
import com.example.ServiceSubscription.exeption.SubscriptionNotFoundException;
import com.example.ServiceSubscription.exeption.UserNotFoundException;
import com.example.ServiceSubscription.mapper.SubscriptionMapper;
import com.example.ServiceSubscription.model.Subscription;
import com.example.ServiceSubscription.model.User;
import com.example.ServiceSubscription.repository.SubscriptionRepository;
import com.example.ServiceSubscription.repository.UserRepository;
import com.example.ServiceSubscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionResponseDto addSubscription(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        log.info("Attempting to add subscription for user ID: {}", userId);
        log.debug("Subscription request data: {}", subscriptionRequestDto);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new UserNotFoundException("User not found with id: " + userId);
                });

        Subscription subscription = subscriptionMapper.toEntity(subscriptionRequestDto);
        subscription.setUser(user);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Successfully added subscription with ID: {} for user ID: {}",
                savedSubscription.getId(), userId);

        return subscriptionMapper.toResponseDto(savedSubscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseDto> getUserSubscriptions(Long userId) {
        log.info("Fetching subscriptions for user ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.error("User not found with ID: {}", userId);
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        List<SubscriptionResponseDto> subscriptions = subscriptionRepository.findByUserId(userId).stream()
                .map(subscriptionMapper::toResponseDto)
                .collect(Collectors.toList());

        log.debug("Found {} subscriptions for user ID: {}", subscriptions.size(), userId);
        return subscriptions;
    }

    @Override
    @Transactional
    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Attempting to delete subscription ID: {} for user ID: {}", subscriptionId, userId);

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> {
                    log.error("Subscription not found with ID: {}", subscriptionId);
                    return new SubscriptionNotFoundException("Subscription not found with id: " + subscriptionId);
                });

        if (!subscription.getUser().getId().equals(userId)) {
            log.error("Subscription ID: {} doesn't belong to user ID: {}", subscriptionId, userId);
            throw new SubscriptionNotFoundException("Subscription not found for user with id: " + userId);
        }

        subscriptionRepository.delete(subscription);
        log.info("Successfully deleted subscription ID: {} for user ID: {}", subscriptionId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseDto> getTop3PopularSubscriptions() {
        log.info("Fetching top 3 popular subscriptions");

        List<SubscriptionResponseDto> topSubscriptions = subscriptionRepository.findTop3PopularSubscriptions().stream()
                .map(result -> new SubscriptionResponseDto(
                        null,
                        (String) result[0],
                        null,
                        null,
                        null
                ))
                .toList();

        log.debug("Top subscriptions: {}", topSubscriptions);
        return topSubscriptions;
    }
}
