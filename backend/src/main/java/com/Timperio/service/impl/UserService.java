package com.Timperio.service.impl;

import java.util.*;
import com.Timperio.models.User;
import com.Timperio.enums.*;

public interface UserService {
    
    public List<User> findAll();

    public User findByUserId(Integer userId);

    public Optional<User> findByUserEmail(String userEmail);

    public Optional<User> findByName(String userName);

    public List<User> findByRole(Role role);
}