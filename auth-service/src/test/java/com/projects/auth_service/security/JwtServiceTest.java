package com.projects.auth_service.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.projects.auth_service.entity.User;
import com.projects.auth_service.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService("test-secret-key-123", "auth-service", 60);
    }

    @Test
    void generates_token_with_correct_claims() {
        User user = User.builder().id(42L).userName("john").role(Role.ROLE_ADMIN).build();

        String token = jwtService.generateToken(user);
        DecodedJWT decoded = jwtService.verifyToken(token);

        assertEquals("42", decoded.getSubject());
        assertEquals("john", decoded.getClaim("username").asString());
        assertEquals("ROLE_ADMIN", decoded.getClaim("role").asString());
    }

    @Test
    void rejects_token_signed_with_different_secret() {
        JwtService otherService = new JwtService("different-secret", "auth-service", 60);
        User user = User.builder().id(1L).userName("john").role(Role.ROLE_USER).build();

        String token = otherService.generateToken(user);

        assertThrows(JWTVerificationException.class, () -> jwtService.verifyToken(token));
    }

    @Test
    void rejects_token_with_wrong_issuer() {
        JwtService otherIssuerService = new JwtService("test-secret-key-123", "some-other-service", 60);
        User user = User.builder().id(1L).userName("john").role(Role.ROLE_USER).build();

        String token = otherIssuerService.generateToken(user);

        assertThrows(JWTVerificationException.class, () -> jwtService.verifyToken(token));
    }
}
