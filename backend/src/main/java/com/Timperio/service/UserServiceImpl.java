package com.Timperio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.Timperio.enums.*;
import com.Timperio.models.*;
import com.Timperio.repository.UserRepository;
import com.Timperio.service.impl.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
