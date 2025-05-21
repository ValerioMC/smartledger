package dev.vmc.smartledger.rest.controller;

import dev.vmc.smartledger.dto.auth.JwtResponse;
import dev.vmc.smartledger.dto.auth.LoginRequest;
import dev.vmc.smartledger.dto.auth.RegisterRequest;
import dev.vmc.smartledger.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    /**
     * Login endpoint.
     *
     * @param loginRequest the login request
     * @return the JWT response
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login request for user: {}", loginRequest.getUsername());
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Registration endpoint.
     *
     * @param registerRequest the registration request
     * @return the JWT response
     */
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Registration request for user: {}", registerRequest.getUsername());
        JwtResponse jwtResponse = authService.registerUser(registerRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Logout endpoint.
     * 
     * @return success message
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // JWT is stateless, so we don't need to do anything on the server side
        // The client should remove the token from storage
        return ResponseEntity.ok("Logout successful");
    }
}