package com.level.tech.controller;

import com.level.tech.dto.request.*;
import com.level.tech.dto.response.AuthenticationResponse;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.security.LogoutService;
import com.level.tech.service.ForgotPasswordService;
import com.level.tech.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class LoginController {

    private final LoginService loginService;

    private final LogoutService logoutService;

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthRequest authRequest,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                loginService.login(authRequest, request), HttpStatus.OK);
    }

    @PostMapping("/generate/otp")
    public ResponseEntity<ResponseData> generateOtp(
            @RequestBody @Valid EmailRequest emailRequest
    ) {
        loginService.generateTokenAndSendOtp(emailRequest.getEmail());
        return new ResponseEntity<>(
                new ResponseData("OTP sent successfully") , HttpStatus.OK);
    }

    @PostMapping("/login/otp")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid OtpRequest otpRequest
    ) {
        return new ResponseEntity<>(
                loginService.otpLogin(otpRequest), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseData> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            Authentication connectedUser
    ) {
        loginService.changePassword(request,connectedUser);
        return new ResponseEntity<>(
                new ResponseData("Password changed successfully"), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseData> forgotPassword(
            @RequestBody @Valid ForgotPasswordRequest request
    ) {
        forgotPasswordService.requestPasswordReset(request);
        return new ResponseEntity<>(
                new ResponseData("Mail sent successfully"), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseData> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        forgotPasswordService.resetPassword(request);
        return new ResponseEntity<>(
                new ResponseData("Password changed successfully"), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseData> logout(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            Authentication authentication
    ) {
        logoutService.logout(request, response, authentication);
        return new ResponseEntity<>(
                new ResponseData("Logged out successfully"), HttpStatus.OK);
    }
}
