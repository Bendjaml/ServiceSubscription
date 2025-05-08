package com.example.ServiceSubscription.mapper;

import com.example.ServiceSubscription.dto.SubscriptionResponseDto;
import com.example.ServiceSubscription.dto.UserRequestDto;
import com.example.ServiceSubscription.dto.UserResponseDto;
import com.example.ServiceSubscription.model.Subscription;
import com.example.ServiceSubscription.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "subscriptions", expression = "java(mapSubscriptions(user.getSubscriptions()))")
    UserResponseDto toResponseDto(User user);

    @Mapping(target = "subscriptions", ignore = true)
    UserResponseDto toResponseDtoWithoutSubscriptions(User user);

    default Set<SubscriptionResponseDto> mapSubscriptions(Set<Subscription> subscriptions) {
        if (subscriptions == null) {
            return new HashSet<>();
        }
        return subscriptions.stream()
                .map(sub -> new SubscriptionResponseDto(
                        sub.getId(),
                        sub.getServiceName(),
                        sub.getStartDate(),
                        sub.getEndDate(),
                        sub.getUser().getId()
                ))
                .collect(Collectors.toSet());
    }
}