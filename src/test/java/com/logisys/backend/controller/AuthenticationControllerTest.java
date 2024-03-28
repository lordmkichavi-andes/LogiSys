package com.logisys.backend.controller;

import com.logisys.backend.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationController authenticationController;

    private Map<String, String> loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new HashMap<>();
        loginRequest.put("username", "testUser");
        loginRequest.put("password", "testPassword");
    }

    @Test
    void authenticateUser_ReturnsToken_WhenCredentialsAreValid() {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenProvider.generateToken(any(Authentication.class))).thenReturn("dummyToken");

        ResponseEntity<?> response = authenticationController.authenticateUser(loginRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertThat(responseBody).containsKey("accessToken");
        assertThat(responseBody.get("accessToken")).isEqualTo("dummyToken");
        assertThat(responseBody).containsKey("tokenType");
        assertThat(responseBody.get("tokenType")).isEqualTo("Bearer");
    }
}
