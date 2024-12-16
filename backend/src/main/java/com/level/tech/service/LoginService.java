package com.level.tech.service;

import com.level.tech.dto.request.AuthRequest;
import com.level.tech.dto.request.ChangePasswordRequest;
import com.level.tech.dto.request.OtpRequest;
import com.level.tech.dto.response.AuthenticationResponse;
import com.level.tech.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface LoginService {

    AuthenticationResponse login(AuthRequest request, HttpServletRequest httpRequest);

    AuthenticationResponse otpLogin(OtpRequest request);

    void generateTokenAndSendOtp(String email);

    void changePassword(ChangePasswordRequest request, Authentication connectUser);

    void saveUserToken(User user, String accessToken);

    AuthenticationResponse generateAuthResponse(User user);

}
