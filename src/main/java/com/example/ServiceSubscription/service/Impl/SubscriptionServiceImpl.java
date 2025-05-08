package com.example.ServiceSubscription.service.Impl;

import com.example.ServiceSubscription.dto.SubscriptionRequestDto;
import com.example.ServiceSubscription.dto.SubscriptionResponseDto;
import com.example.ServiceSubscription.dto.TopSubscriptionResponseDto;
import com.example.ServiceSubscription.exeption.SubscriptionNotFoundException;
import com.example.ServiceSubscription.exeption.UserNotFoundException;
import com.example.ServiceSubscription.mapper.SubscriptionMapper;
import com.example.ServiceSubscription.model.Subscription;
import com.example.ServiceSubscription.model.User;
import com.example.ServiceSubscription.repository.SubscriptionRepository;
import com.example.ServiceSubscription.repository.UserRepository;
import com.example.ServiceSubscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionResponseDto addSubscription(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Subscription subscription = subscriptionMapper.toEntity(subscriptionRequestDto);
        subscription.setUser(user);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponseDto(savedSubscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseDto> getUserSubscriptions(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscriptionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSubscription(Long userId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found with id: " + subscriptionId));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new SubscriptionNotFoundException("Subscription not found for user with id: " + userId);
        }

        subscriptionRepository.delete(subscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopSubscriptionResponseDto> getTop3PopularSubscriptions() {
        List<Object[]> results = subscriptionRepository.findTop3PopularSubscriptions();
        return results.stream()
                .map(result -> subscriptionMapper.toTopResponseDto(
                        (String) result[0],
                        (Long) result[1]))
                .collect(Collectors.toList());
    }
}