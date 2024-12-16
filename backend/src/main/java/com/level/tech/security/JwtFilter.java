package com.level.tech.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.level.tech.exception.ExpiredJwtTokenException;
import com.level.tech.exception.handler.ExceptionResponse;
import com.level.tech.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private static final String TOKEN_TYPE = "BEARER ";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null ) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHead = TOKEN_TYPE + authHeader;

        jwt = authHead.substring(7);

        if (tokenRepository.findByJwtToken(jwt).isEmpty()) {
            var exception = ExceptionResponse.builder()
                    .message("Invalid token ! Login Again")
                    .build();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(new ObjectMapper().writeValueAsString(exception));
            return;
        }

        try {
            userEmail = jwtService.extractUserName(jwt);
        } catch (ExpiredJwtTokenException e) {
            var exception = ExceptionResponse.builder()
                    .message("Your session has expired. Please log in again.")
                    .build();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(new ObjectMapper().writeValueAsString(exception));
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            var isTokenValid = tokenRepository.findByJwtToken(jwt)
                    .map(t -> !t.getExpired() && !t.getRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails) && Boolean.TRUE.equals(isTokenValid)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                var exception = ExceptionResponse.builder()
                        .message("Your session has expired. Please log in again.")
                        .build();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(new ObjectMapper().writeValueAsString(exception));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}