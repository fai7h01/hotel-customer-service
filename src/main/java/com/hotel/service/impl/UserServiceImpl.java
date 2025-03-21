package com.hotel.service.impl;

import com.hotel.dto.UserDTO;
import com.hotel.entity.User;
import com.hotel.exception.UserAlreadyExistsException;
import com.hotel.exception.UserNotFoundException;
import com.hotel.mapper.UserMapper;
import com.hotel.repository.UserRepository;
import com.hotel.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDTO save(UserDTO user) {
        validateUserCreate(user);
        var userEntity = userMapper.toEntity(user);
        var savedEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedEntity);
    }

    @Override
    public UserDTO findByEmail(String email) {
        var foundUser = findUserEntityByEmail(email);
        return userMapper.toDto(foundUser);
    }

    @Override
    public UserDTO findById(Long id) {
        var foundUser = findUserEntityById(id);
        return userMapper.toDto(foundUser);
    }

    private User findUserEntityByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    private User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    private void validateUserCreate(UserDTO user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use.");
        }
    }
}
