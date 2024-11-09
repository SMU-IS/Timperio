package com.Timperio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.Timperio.enums.*;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class CreateUserDto {
    private String userEmail;     
    private String password;   
    private String name;
    private Role role;
}