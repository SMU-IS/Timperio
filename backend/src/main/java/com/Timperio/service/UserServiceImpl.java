package com.Timperio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.Timperio.enums.*;
import com.Timperio.models.*;
import com.Timperio.dto.*;
import com.Timperio.repository.UserRepository;
import com.Timperio.service.impl.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(CreateUserDto input) {
        User user;
    
        switch (input.getRole()) {
            case ADMIN:
                user = new AdminUser(); 
                break;
            case MARKETING:
                user = new MarketingUser();
                break;
            case SALES:
                user = new SalesUser();
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + input.getRole());
        }
    
        user.setName(input.getName());
        user.setUserEmail(input.getUserEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
    
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    public Optional<User> findByName(String userName) {
        return userRepository.findByName(userName);
    }

    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public void saveUser(MarketingUser user) {
        userRepository.save(user);
    }

    public void saveUser(SalesUser user) {
        userRepository.save(user);
    }

    public void saveUser(AdminUser user) {
        userRepository.save(user);
    }

}
