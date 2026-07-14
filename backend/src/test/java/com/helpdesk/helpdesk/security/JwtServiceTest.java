package com.helpdesk.helpdesk.security;

import com.helpdesk.helpdesk.entity.Role;
import com.helpdesk.helpdesk.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Set properties via reflection since it's a unit test without Spring Context
        ReflectionTestUtils.setField(jwtService, "secretKey", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1000 * 60 * 60); // 1 hour
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 1000 * 60 * 60 * 24); // 24 hours
    }

    @Test
    void shouldGenerateAndValidateToken() {
        // Arrange
        Usuario user = Usuario.builder()
                .email("test@test.com")
                .role(Role.USUARIO)
                .build();

        // Act
        String token = jwtService.generateToken(user);
        
        // Assert
        assertNotNull(token);
        String username = jwtService.extractUsername(token);
        assertEquals(user.getUsername(), username);
        assertTrue(jwtService.isTokenValid(token, user));
    }
}
