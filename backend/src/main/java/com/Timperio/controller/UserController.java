package com.Timperio.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.Timperio.enums.Role;
import com.Timperio.models.User;
import com.Timperio.service.impl.UserService;
import com.Timperio.dto.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        User newUser = this.userService.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer userId) {
        this.userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/email/{userEmail}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String userEmail) {
        this.userService.deleteUserByEmail(userEmail);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody UpdateUserDto updateUserDto) {
        User updatedUser = this.userService.updateUser(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping()
    public List<User> getUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId) {
        return this.userService.findByUserId(userId);
    }

    @GetMapping("/email/{userEmail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String userEmail) {
        Optional<User> optionalUser = userService.findByUserEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user); // Return user with HTTP 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found
        }
    }

    @GetMapping("/name/{userName}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        Optional<User> optionalUser = userService.findByName(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user); // Return user with HTTP 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found
        }
    }

    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable Role role) {
        return this.userService.findByRole(role);
    }

}