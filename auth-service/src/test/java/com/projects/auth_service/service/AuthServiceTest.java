package com.projects.auth_service.service;

import com.projects.auth_service.dto.AuthResponse;
import com.projects.auth_service.dto.LoginRequest;
import com.projects.auth_service.dto.RegisterRequest;
import com.projects.auth_service.entity.User;
import com.projects.auth_service.enums.Role;
import com.projects.auth_service.exception.InvalidCredentialsException;
import com.projects.auth_service.exception.ResourceAlreadyExistsException;
import com.projects.auth_service.mapper.UserMapper;
import com.projects.auth_service.repository.UserRepository;
import com.projects.auth_service.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_createsUserAndReturnsToken() {
        RegisterRequest request = new RegisterRequest("john", "john@mail.com", "password123");
        User mappedUser = User.builder().userName("john").email("john@mail.com").password("hashed").role(Role.ROLE_USER).build();
        User savedUser = User.builder().id(1L).userName("john").email("john@mail.com").password("hashed").role(Role.ROLE_USER).build();
        AuthResponse expectedResponse = AuthResponse.builder().token("fake-jwt").userId(1L).username("john").role(Role.ROLE_USER).build();

        when(userRepository.existsByUserName("john")).thenReturn(false);
        when(userRepository.existsByEmail("john@mail.com")).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(mappedUser);
        when(userRepository.save(mappedUser)).thenReturn(savedUser);
        when(jwtService.generateToken(savedUser)).thenReturn("fake-jwt");
        when(userMapper.toAuthResponse(savedUser, "fake-jwt")).thenReturn(expectedResponse);

        AuthResponse response = authService.register(request);

        assertEquals("fake-jwt", response.getToken());
    }

    @Test
    void register_rejectsDuplicateUsername() {
        RegisterRequest request = new RegisterRequest("john", "john@mail.com", "password123");
        when(userRepository.existsByUserName("john")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_succeedsWithCorrectPassword() {
        LoginRequest request = new LoginRequest("john", "password123");
        User user = User.builder().id(1L).userName("john").password("hashed").role(Role.ROLE_USER).build();
        AuthResponse expectedResponse = AuthResponse.builder().token("fake-jwt").userId(1L).username("john").role(Role.ROLE_USER).build();

        when(userRepository.findByUserName("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashed")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("fake-jwt");
        when(userMapper.toAuthResponse(user, "fake-jwt")).thenReturn(expectedResponse);

        AuthResponse response = authService.login(request);

        assertEquals("fake-jwt", response.getToken());
    }

    @Test
    void login_rejectsWrongPassword() {
        LoginRequest request = new LoginRequest("john", "wrong-password");
        User user = User.builder().id(1L).userName("john").password("hashed").role(Role.ROLE_USER).build();

        when(userRepository.findByUserName("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "hashed")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
        verify(jwtService, never()).generateToken(any());
    }
}
