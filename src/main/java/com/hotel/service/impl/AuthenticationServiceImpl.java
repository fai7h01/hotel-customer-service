package com.hotel.service.impl;

import com.hotel.dto.UserDTO;
import com.hotel.exception.UserNotFoundException;
import com.hotel.service.AuthenticationService;
import com.hotel.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDTO getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("email");
            if (username != null) {
                return userService.findByEmail(username);
            }
        }
        throw new UserNotFoundException("No authenticated user found");
    }

    @Override
    public boolean isUserAnonymous() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> auth.getAuthorities().stream()
                        .anyMatch(a -> "ROLE_ANONYMOUS".equals(a.getAuthority())))
                .orElse(true);
    }
}
