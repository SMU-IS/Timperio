package com.Timperio.service;

import com.Timperio.dto.*;
import com.Timperio.models.*;
import com.Timperio.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Timperio.service.impl.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
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

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    input.getUserEmail(),
                    input.getPassword()
            )
        );

        return userRepository.findByUserEmail(input.getUserEmail())
                .orElseThrow();
    }
}