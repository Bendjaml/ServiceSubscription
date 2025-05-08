package com.example.ServiceSubscription.service.Impl;

import com.example.ServiceSubscription.dto.UserRequestDto;
import com.example.ServiceSubscription.dto.UserResponseDto;
import com.example.ServiceSubscription.exeption.UserNotFoundException;
import com.example.ServiceSubscription.mapper.UserMapper;
import com.example.ServiceSubscription.model.User;
import com.example.ServiceSubscription.repository.UserRepository;
import com.example.ServiceSubscription.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDtoWithoutSubscriptions(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setName(userRequestDto.getName());
        existingUser.setEmail(userRequestDto.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDtoWithoutSubscriptions(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDtoWithoutSubscriptions)
                .collect(Collectors.toList());
    }
}