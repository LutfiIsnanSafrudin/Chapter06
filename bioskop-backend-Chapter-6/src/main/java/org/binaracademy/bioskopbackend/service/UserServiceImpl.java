package org.binaracademy.bioskopbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.bioskopbackend.model.User;
import org.binaracademy.bioskopbackend.model.response.UserResponse;
import org.binaracademy.bioskopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponse loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username " + username));
        return UserResponse.build(user);
    }
}
