package com.example.ServiceSubscription.mapper;

import com.example.ServiceSubscription.dto.SubscriptionRequestDto;
import com.example.ServiceSubscription.dto.SubscriptionResponseDto;
import com.example.ServiceSubscription.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = {},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subscription toEntity(SubscriptionRequestDto subscriptionRequestDto);

    @Mapping(target = "userId", source = "user.id")
    SubscriptionResponseDto toResponseDto(Subscription subscription);

}