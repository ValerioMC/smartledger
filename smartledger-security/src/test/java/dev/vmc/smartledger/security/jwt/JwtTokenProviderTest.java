package dev.vmc.smartledger.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class JwtTokenProviderTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private final String secretKey = "test-secret-key-for-jwt-should-be-at-least-32-characters";
    private final long validityInMilliseconds = 3600000; // 1h
    private final String username = "testuser";
    private final List<GrantedAuthority> authorities = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        // Set up test data
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        // Initialize the token provider with test values
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", validityInMilliseconds);
        
        // Call the init method manually
        jwtTokenProvider.init();
    }

    @Test
    void createToken_ShouldCreateValidToken() {
        // Act
        String token = jwtTokenProvider.createToken(username, authorities);
        
        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals(username, jwtTokenProvider.getUsername(token));
    }

    @Test
    void getUsername_ShouldReturnCorrectUsername() {
        // Arrange
        String token = jwtTokenProvider.createToken(username, authorities);
        
        // Act
        String extractedUsername = jwtTokenProvider.getUsername(token);
        
        // Assert
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        // Arrange
        String token = jwtTokenProvider.createToken(username, authorities);
        
        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseForInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.string";
        
        // Act
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        
        // Assert
        assertFalse(isValid);
    }

    @Test
    void getAuthentication_ShouldReturnCorrectAuthentication() {
        // Arrange
        String token = jwtTokenProvider.createToken(username, authorities);
        UserDetails userDetails = new User(username, "", authorities);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        
        // Act
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        
        // Assert
        assertNotNull(authentication);
        assertEquals(username, authentication.getName());
        assertEquals(authorities.size(), authentication.getAuthorities().size());
    }

    @Test
    void resolveToken_ShouldExtractTokenFromAuthorizationHeader() {
        // Arrange
        String token = "test-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        
        // Act
        String resolvedToken = jwtTokenProvider.resolveToken(request);
        
        // Assert
        assertEquals(token, resolvedToken);
    }

    @Test
    void resolveToken_ShouldReturnNullWhenNoAuthorizationHeader() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);
        
        // Act
        String resolvedToken = jwtTokenProvider.resolveToken(request);
        
        // Assert
        assertNull(resolvedToken);
    }

    @Test
    void resolveToken_ShouldReturnNullWhenInvalidAuthorizationHeader() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Invalid token");
        
        // Act
        String resolvedToken = jwtTokenProvider.resolveToken(request);
        
        // Assert
        assertNull(resolvedToken);
    }
}