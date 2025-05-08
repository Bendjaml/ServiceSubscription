package com.example.ServiceSubscription.service;

import com.example.ServiceSubscription.dto.UserRequestDto;
import com.example.ServiceSubscription.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUser(Long id);
    List<UserResponseDto> getAllUsers();
}