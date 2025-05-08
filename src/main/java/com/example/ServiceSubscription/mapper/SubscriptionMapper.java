package com.example.ServiceSubscription.mapper;

import com.example.ServiceSubscription.dto.SubscriptionRequestDto;
import com.example.ServiceSubscription.dto.SubscriptionResponseDto;
import com.example.ServiceSubscription.dto.TopSubscriptionResponseDto;
import com.example.ServiceSubscription.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubscriptionMapper {
    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    Subscription toEntity(SubscriptionRequestDto subscriptionRequestDto);

    @Mapping(target = "userId", source = "user.id")
    SubscriptionResponseDto toResponseDto(Subscription subscription);

    @Mapping(target = "serviceName", source = "serviceName")
    @Mapping(target = "subscribersCount", source = "count")
    TopSubscriptionResponseDto toTopResponseDto(String serviceName, Long count);
}