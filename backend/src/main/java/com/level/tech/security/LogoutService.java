package com.level.tech.security;

import com.level.tech.exception.BadCredentialException;
import com.level.tech.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    private static final String TOKEN_TYPE = "BEARER ";

    @Override
    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if(authHeader == null) {
            throw new BadCredentialException("You're already logged out");
        }

        String authHead = TOKEN_TYPE + authHeader;

        jwt = authHead.substring(7);
        var storedToken = tokenRepository.findByJwtToken(jwt)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);

            tokenRepository.save(storedToken);
        }
    }
}
