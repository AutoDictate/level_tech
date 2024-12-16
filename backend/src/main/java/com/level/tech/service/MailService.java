package com.level.tech.service;

import com.level.tech.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MailService {

    void htmlMailSender(User user, String subject, String description, List<MultipartFile> multipartFile);

    void sendOtpEmail(final User user, final Integer otp, boolean isRegistration);

    void sendResetEmail(final User user, final String resetLink);

}
