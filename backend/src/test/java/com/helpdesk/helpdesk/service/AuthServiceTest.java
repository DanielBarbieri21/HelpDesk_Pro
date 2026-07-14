package com.helpdesk.helpdesk.service;

import com.helpdesk.helpdesk.dto.AuthResponseDTO;
import com.helpdesk.helpdesk.dto.LoginRequestDTO;
import com.helpdesk.helpdesk.dto.RegisterRequestDTO;
import com.helpdesk.helpdesk.entity.Role;
import com.helpdesk.helpdesk.entity.Usuario;
import com.helpdesk.helpdesk.mapper.UsuarioMapper;
import com.helpdesk.helpdesk.repository.UsuarioRepository;
import com.helpdesk.helpdesk.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO("Test", "test@test.com", "123456", "USUARIO");
        Usuario mappedUser = new Usuario();
        
        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(mapper.toEntity(request)).thenReturn(mappedUser);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any())).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");

        // Act
        AuthResponseDTO response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals(Role.USUARIO, mappedUser.getRole());
        assertEquals("encodedPassword", mappedUser.getPassword());
        verify(repository).save(mappedUser);
    }

    @Test
    void shouldThrowExceptionWhenRegisteringExistingEmail() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO("Test", "test@test.com", "123456", "USUARIO");
        
        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Usuario()));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.register(request));
        assertEquals("E-mail já cadastrado", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldLoginUserSuccessfully() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO("test@test.com", "123456");
        Usuario user = new Usuario();
        user.setEmail(request.getEmail());

        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        // Act
        AuthResponseDTO response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
