package com.level.tech.service;

import com.level.tech.entity.User;
import com.level.tech.exception.BadCredentialException;
import com.level.tech.exception.FileProcessingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final SpringTemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void htmlMailSender(
            final User user,
            final String subject,
            final String description,
            final List<MultipartFile> multipartFiles
    ) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    "UTF-8"
            );
            helper.setFrom("jayasurya0206@gmail.com");
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(description, true); // true for HTML content

            // Attach files
            if (multipartFiles != null && !multipartFiles.isEmpty()) {
                for (MultipartFile file : multipartFiles) {
                    if (!file.isEmpty()) {
                        // Save multipart file to a temporary file
                        File tempFile = convertMultipartFileToFile(file);
                        helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), tempFile);
                    }
                }
            }
            // Send the email
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadCredentialException("Failed to send email");
        }
    }

    @Async
    @Override
    public void sendOtpEmail(final User user, final Integer otp, boolean isRegistration) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("otp", otp);

        String emailBody = templateEngine.process("emails/login_otp", context);
        String subject = "Your One-Time Password for Login";
        htmlMailSender(user, subject, emailBody, null);
    }

    @Async
    @Override
    public void sendResetEmail(final User user, final String resetLink) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("resetLink", resetLink);

        String emailBody;
        String subject;

        if (resetLink != null) {
            emailBody = templateEngine.process("emails/reset_password", context);
            subject = "Reset Password";
        } else {
            emailBody = templateEngine.process("emails/reset_password", context);
            subject = "Password Changed Successfully !!";
        }
        htmlMailSender(user, subject, emailBody, null);
    }

    private File convertMultipartFileToFile(
            final MultipartFile multipartFile
    ) throws FileProcessingException {

        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileProcessingException("Failed to convert MultipartFile to File");
        }
        return file;
    }
}
