package com.level.tech.service;

import com.level.tech.dto.request.AuthRequest;
import com.level.tech.dto.request.ChangePasswordRequest;
import com.level.tech.dto.request.OtpRequest;
import com.level.tech.dto.response.AuthenticationResponse;
import com.level.tech.entity.Otp;
import com.level.tech.entity.Token;
import com.level.tech.entity.User;
import com.level.tech.exception.BadCredentialException;
import com.level.tech.exception.NotFoundException;
import com.level.tech.repository.TokenRepository;
import com.level.tech.repository.UserRepository;
import com.level.tech.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final MailService mailService;

    private final OtpService otpService;

    private final JwtService jwtService;

    private static final String USER_NOT_FOUND = "User Not found for the email";

    @Override
    public AuthenticationResponse login(
            final AuthRequest request,
            final HttpServletRequest httpRequest
    ) {
        Authentication auth = authenticateUser(request);
        var authUser = ((User) auth.getPrincipal());

        return generateAuthResponse(authUser);
    }

    @Override
    public AuthenticationResponse otpLogin(final OtpRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        Boolean verified = otpService.verifyOtp(request.getOtp(), user.getId());

        if (Boolean.TRUE.equals(verified)) {
            userRepository.save(user);
            return generateAuthResponse(user);
        }
        throw new BadCredentialException("OTP is wrong");
    }

    @Override
    public void generateTokenAndSendOtp(final String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        Otp otp = otpService.generateOtp(user);
        mailService.sendOtpEmail(user, otp.getValue(), false);
    }

    @Override
    public void changePassword(
            final ChangePasswordRequest request,
            Authentication authUser
    ) {
        var connectedUser = (User) authUser.getPrincipal();
        User user = userRepository.findById(connectedUser.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        var existingPassword = user.getPassword();
        var newPassword = request.getNewPassword();

        if (! passwordEncoder.matches(request.getCurrentPassword(), existingPassword)) {
            throw new BadCredentialException("Wrong Current Password");
        }
        if (passwordEncoder.matches(newPassword, existingPassword)) {
            throw new BadCredentialException("Please provide a different password");
        }
        if (! request.getConfirmationPassword().equals(newPassword)) {
            throw new BadCredentialException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveUserToken(
            final User user,
            final String accessToken
    ) {
        var token = Token.builder()
                .jwtToken(accessToken)
                .user(user)
                .tokenType("BEARER")
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private Authentication authenticateUser(AuthRequest request) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Username or Password is incorrect");
        }
    }

    @Override
    public AuthenticationResponse generateAuthResponse(final User user) {
        var claims = new HashMap<String, Object>();
        claims.put("name", user.getUsername());
        claims.put("token_type", "access_token");
        claims.put("aud", "back2u");

        //Generate Token
        var jwtToken = jwtService.generateToken(claims, user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().getName())
                .termsAndConditions(user.getTermsAndConditions())
                .build();
    }

    private void revokeAllUserTokens(final User user) {
        List<Token> validUserAccessTokens = tokenRepository.findAllUserAccessTokens(user.getId());
        if (validUserAccessTokens.isEmpty()) {
            return;
        }
        validUserAccessTokens.forEach(
                t -> {
                    t.setRevoked(true);
                    t.setExpired(true);
                }
        );
        tokenRepository.saveAll(validUserAccessTokens);
    }
}
