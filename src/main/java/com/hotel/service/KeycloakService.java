package com.hotel.service;

import com.hotel.dto.UserDTO;

public interface KeycloakService {

    void userCreate(UserDTO dto);
    void userUpdate(UserDTO dto);
}
