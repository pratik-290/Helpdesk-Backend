package com.example.demo.helpdesk.service;

import com.example.demo.helpdesk.dto.RegisterRequest;
import com.example.demo.helpdesk.dto.UserResponse;
import com.example.demo.helpdesk.entity.User;
import com.example.demo.helpdesk.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.demo.helpdesk.exception.EmailAlreadyExistsException;
import com.example.demo.helpdesk.dto.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.helpdesk.exception.InvalidCredentialsException;
import com.example.demo.helpdesk.service.JwtService;
import com.example.demo.helpdesk.dto.LoginResponse;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService = jwtService;
    }


    public UserResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");        }
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));        user.setRole("CUSTOMER");
        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
    public LoginResponse loginUser(LoginRequest request) {

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        String token = jwtService.generateToken(user);

        return new LoginResponse(token, userResponse);

    }
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();
    }

    public UserResponse updateUserRole(Long id, String role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newRole = role.toUpperCase();

        if (!newRole.equals("CUSTOMER") &&
                !newRole.equals("AGENT") &&
                !newRole.equals("ADMIN")) {
            throw new RuntimeException("Invalid role");
        }

        user.setRole(newRole);

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
}