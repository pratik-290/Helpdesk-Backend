package com.example.demo.helpdesk.controller;

import com.example.demo.helpdesk.dto.LoginRequest;
import com.example.demo.helpdesk.dto.RegisterRequest;
import com.example.demo.helpdesk.dto.UserResponse;
import com.example.demo.helpdesk.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.demo.helpdesk.dto.LoginResponse;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse registerUser(@Valid @RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping("/profile")
    public String profile() {
        return "You are authenticated";
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}