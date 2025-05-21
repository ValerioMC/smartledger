package dev.vmc.smartledger.rest.controller;

import dev.vmc.smartledger.dto.auth.JwtResponse;
import dev.vmc.smartledger.dto.auth.LoginRequest;
import dev.vmc.smartledger.dto.auth.RegisterRequest;
import dev.vmc.smartledger.security.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        loginRequest = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        registerRequest = RegisterRequest.builder()
                .username("newuser")
                .email("newuser@example.com")
                .password("password123")
                .firstName("New")
                .lastName("User")
                .build();

        List<String> roles = Arrays.asList("ROLE_USER");
        jwtResponse = JwtResponse.builder()
                .token("test.jwt.token")
                .type("Bearer")
                .id(1L)
                .username("testuser")
                .email("testuser@example.com")
                .roles(roles)
                .firstName("Test")
                .lastName("User")
                .build();
    }

    @Test
    void login_ShouldReturnJwtResponse() {
        // Arrange
        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(jwtResponse);

        // Act
        ResponseEntity<JwtResponse> response = authController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(jwtResponse, response.getBody());
    }

    @Test
    void register_ShouldReturnJwtResponse() {
        // Arrange
        when(authService.registerUser(any(RegisterRequest.class))).thenReturn(jwtResponse);

        // Act
        ResponseEntity<JwtResponse> response = authController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(jwtResponse, response.getBody());
    }

    @Test
    void logout_ShouldReturnSuccessMessage() {
        // Act
        ResponseEntity<String> response = authController.logout();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Logout successful", response.getBody());
    }
}