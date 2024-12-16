package com.level.tech.service;

import com.level.tech.entity.Otp;
import com.level.tech.entity.User;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.OtpRepository;
import com.level.tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final SecureRandom random = new SecureRandom();

    private final UserRepository userRepository;

    private final OtpRepository otpRepository;

    @Override
    @Transactional
    public Otp generateOtp(final User user) {
        if (userExists(user.getId())) {
            deleteOtp(user.getId());
        }

        //Generate OTP
        Integer value = 100000 + random.nextInt(900000);
        Instant expiryDate = Instant.now().plus(2, ChronoUnit.MINUTES);
        var otp =  Otp.builder()
                .value(value)
                .user(user)
                .expiryDate(expiryDate)
                .build();
        return otpRepository.save(otp);
    }

    @Override
    public Boolean verifyOtp(final Integer otp, final Long userId) {
        if (!userExists(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        Boolean verified =  otpRepository.findByUserId(userId)
                .map(otpValue ->
                        otpValue.getValue().equals(otp)
                                &&
                        !otpValue.isExpired()
                )
                .orElse(Boolean.FALSE);

        if (Boolean.TRUE.equals(verified)) {
            deleteOtp(userId);
        }
        return verified;
    }

    @Override
    @Transactional
    public void deleteOtp(final Long userId) {
        otpRepository.findByUserId(userId).ifPresent(
                otp -> {
                    otpRepository.deleteById(otp.getId());
                    otpRepository.flush();
                });
    }

    private boolean userExists(final Long userId) {
        return userRepository.existsById(userId) && otpRepository.findByUserId(userId).isPresent();
    }
}
