package com.logisys.backend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenFilterTest {

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void givenValidToken_whenDoFilter_thenAuthenticationIsSet() throws Exception {
        String token = "validToken";
        request.addHeader("Authorization", "Bearer " + token);

        when(tokenProvider.validateToken(token)).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(tokenProvider.getAuthentication(token)).thenReturn(authentication);

        jwtTokenFilter.doFilter(request, response, filterChain);

        verify(tokenProvider).getAuthentication(token);
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == authentication;
    }

    @Test
    void givenInvalidToken_whenDoFilter_thenAuthenticationIsNotSet() throws Exception {
        String token = "invalidToken";
        request.addHeader("Authorization", "Bearer " + token);

        when(tokenProvider.validateToken(token)).thenReturn(false);

        jwtTokenFilter.doFilter(request, response, filterChain);

        verify(tokenProvider, never()).getAuthentication(any(String.class));
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Test
    void givenNoToken_whenDoFilter_thenProceedWithoutSettingAuthentication() throws Exception {
        jwtTokenFilter.doFilter(request, response, filterChain);

        verify(tokenProvider, never()).validateToken(any(String.class));
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
