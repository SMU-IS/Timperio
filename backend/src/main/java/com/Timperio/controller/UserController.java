package com.Timperio.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.Timperio.enums.Role;
import com.Timperio.models.User;
import com.Timperio.service.impl.UserService;
import com.Timperio.dto.*;
import com.Timperio.exceptions.*;

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

    // @PutMapping("/{userId}")
    // public ResponseEntity<User> updateUser(@PathVariable String userEmail, @RequestBody UpdateUserDto updateUserDto, @AuthenticationPrincipal UserDetails currentUser) {
    //     if (!userEmail.equals(currentUser.getUsername())) {
    //         throw new AccessDeniedException("You can only update your own account");
    //     }
    //     User updatedUser = this.userService.updateUser(userEmail, updateUserDto);
    //     return ResponseEntity.ok(updatedUser);
    // }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/{userId}")
    public ResponseEntity<Object> updateUserAdmin(@PathVariable Integer userId, @RequestBody UpdateUserAdminDto updateUserDto) {
        try {
            User updatedUser = this.userService.updateUserAdmin(userId, updateUserDto);
            return ResponseEntity.ok(updatedUser);
        } catch (AdminAccountUpdateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());  
        } catch (InvalidRoleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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