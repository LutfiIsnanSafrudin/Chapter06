package org.binaracademy.bioskopbackend.service;


import org.binaracademy.bioskopbackend.model.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface LoginRegisterService {

    public void registerOauth2User(Authentication authentication);
    public Optional<User> loginOauth2User(Authentication authentication);
}
