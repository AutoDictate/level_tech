package com.level.tech.service;

import com.level.tech.dto.request.ForgotPasswordRequest;
import com.level.tech.dto.request.ResetPasswordRequest;

public interface ForgotPasswordService {

    void requestPasswordReset(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
