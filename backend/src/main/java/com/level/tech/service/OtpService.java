package com.level.tech.service;

import com.level.tech.entity.Otp;
import com.level.tech.entity.User;

public interface OtpService {

    Otp generateOtp(User user);

    Boolean verifyOtp(Integer otp, Long userId);

    void deleteOtp(Long userId);
}
