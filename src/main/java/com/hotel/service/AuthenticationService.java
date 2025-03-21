package com.hotel.service;

import com.hotel.dto.UserDTO;

public interface AuthenticationService {

    UserDTO getLoggedInUser();
    boolean isUserAnonymous();
}
