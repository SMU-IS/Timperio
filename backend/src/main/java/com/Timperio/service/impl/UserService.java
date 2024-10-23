package com.Timperio.service.impl;

import java.util.*;
import com.Timperio.models.User;
import com.Timperio.enums.*;

public interface UserService {
    
    public List<User> findAll();

    public User findByUserId(Integer userId);

    public User findByUserEmail(String userEmail);

    public User findByUserName(String userName);

    public List<User> findByRole(Role role);
}