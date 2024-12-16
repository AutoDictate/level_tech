package com.level.tech.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Current password is mandatory")
    @Schema(example = "User@123")
    private String currentPassword;

    @NotBlank(message = "New password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one letter, one number, and one special character"
    )
    @Schema(example = "User@111")
    private String newPassword;

    @NotBlank(message = "Confirmation password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(example = "User@111")
    private String confirmationPassword;
}
