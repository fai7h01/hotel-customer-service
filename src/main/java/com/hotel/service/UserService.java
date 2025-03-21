package com.hotel.service;

import com.hotel.dto.UserDTO;

public interface UserService {

    UserDTO save(UserDTO userDTO);
    UserDTO findByEmail(String email);
    UserDTO findById(Long id);
}
