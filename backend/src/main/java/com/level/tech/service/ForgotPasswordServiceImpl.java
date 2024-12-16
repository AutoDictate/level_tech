package com.level.tech.service;

import com.level.tech.dto.request.ForgotPasswordRequest;
import com.level.tech.dto.request.ResetPasswordRequest;
import com.level.tech.entity.PasswordResetToken;
import com.level.tech.entity.User;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.PasswordResetTokenRepository;
import com.level.tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService{

    private final MailService mailService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${password.reset.link}")
    private String resetURL;

    @Override
    @Transactional
    public void requestPasswordReset(
            final ForgotPasswordRequest request
    ) {
        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Email not found"));

        passwordResetTokenRepository.findByUserId(user.getId())
                .ifPresent(passwordResetToken -> {
                    passwordResetTokenRepository.delete(passwordResetToken);
                    passwordResetTokenRepository.flush();
                });

        // Generate token
        String token = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plus(1, ChronoUnit.HOURS);

        PasswordResetToken resetToken = new PasswordResetToken(user, token, expiryDate);
        passwordResetTokenRepository.save(resetToken);

        // Send reset email
        String resetLink = resetURL + token;
        mailService.sendResetEmail(user, resetLink);
    }

    @Override
    @Transactional
    public void resetPassword(
            final ResetPasswordRequest request
    ) {
        var token = request.getToken();
        var newPassword = request.getNewPassword();
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Invalid reset token"));

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Optionally, delete or invalidate the token
        passwordResetTokenRepository.delete(resetToken);
        mailService.sendResetEmail(user, null);
    }
}

