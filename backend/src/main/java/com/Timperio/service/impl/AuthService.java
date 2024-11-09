package com.Timperio.service.impl;

import com.Timperio.dto.RegisterUserDto;
import com.Timperio.dto.LoginUserDto;
import com.Timperio.models.User;

public interface AuthService {
    User signup(RegisterUserDto input);
    User authenticate(LoginUserDto input);
}
