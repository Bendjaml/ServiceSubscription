package com.example.ServiceSubscription.mapper;


import com.example.ServiceSubscription.dto.UserRequestDto;
import com.example.ServiceSubscription.dto.UserResponseDto;
import com.example.ServiceSubscription.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = SubscriptionMapper.class)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toResponseDto(User user);

    @Mapping(target = "subscriptions", ignore = true)
    UserResponseDto toResponseDtoWithoutSubscriptions(User user);
}