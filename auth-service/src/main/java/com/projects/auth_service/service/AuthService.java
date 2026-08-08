package com.projects.auth_service.service;

import com.projects.auth_service.dto.AuthResponse;
import com.projects.auth_service.dto.LoginRequest;
import com.projects.auth_service.dto.RegisterRequest;
import com.projects.auth_service.entity.User;
import com.projects.auth_service.exception.InvalidCredentialsException;
import com.projects.auth_service.exception.ResourceAlreadyExistsException;
import com.projects.auth_service.mapper.UserMapper;
import com.projects.auth_service.repository.UserRepository;
import com.projects.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUserName(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        User saved = userRepository.save(userMapper.toEntity(request));
        String token = jwtService.generateToken(saved);

        return userMapper.toAuthResponse(saved, token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        return userMapper.toAuthResponse(user, token);
    }
}
