package com.example.ServiceSubscription.service.Impl;

import com.example.ServiceSubscription.dto.UserRequestDto;
import com.example.ServiceSubscription.dto.UserResponseDto;
import com.example.ServiceSubscription.exeption.UserNotFoundException;
import com.example.ServiceSubscription.mapper.UserMapper;
import com.example.ServiceSubscription.model.User;
import com.example.ServiceSubscription.repository.UserRepository;
import com.example.ServiceSubscription.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Attempting to create new user with email: {}", userRequestDto.getEmail());

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            log.error("Email already in use: {}", userRequestDto.getEmail());
            throw new IllegalArgumentException("Email already in use");
        }

        User user = userMapper.toEntity(userRequestDto);
        User savedUser = userRepository.save(user);

        log.info("Successfully created user with ID: {} and email: {}",
                savedUser.getId(), savedUser.getEmail());

        return userMapper.toResponseDtoWithoutSubscriptions(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });

        log.debug("Found user: {}", user);
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        log.info("Attempting to update user with ID: {}", id);
        log.debug("Update data: {}", userRequestDto);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });

        existingUser.setName(userRequestDto.getName());
        existingUser.setEmail(userRequestDto.getEmail());

        User updatedUser = userRepository.save(existingUser);
        log.info("Successfully updated user with ID: {}", id);

        return userMapper.toResponseDtoWithoutSubscriptions(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Attempting to delete user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            log.error("User not found with ID: {}", id);
            throw new UserNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        log.info("Successfully deleted user with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        log.info("Fetching all users");

        List<UserResponseDto> users = userRepository.findAll().stream()
                .map(userMapper::toResponseDtoWithoutSubscriptions)
                .collect(Collectors.toList());

        log.debug("Found {} users", users.size());
        return users;
    }
}