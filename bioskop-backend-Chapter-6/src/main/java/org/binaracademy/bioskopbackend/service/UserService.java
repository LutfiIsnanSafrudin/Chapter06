package org.binaracademy.bioskopbackend.service;

import org.binaracademy.bioskopbackend.model.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserResponse loadUserByUsername(String username);
}
