package com.level.tech.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {

    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
            message = "Email is not valid")
    @Schema(example = "admin@gmail.com")
    private String email;

    @Digits(integer = 10, fraction = 0, message = "OTP must be a valid number")
    @NotNull(message = "OTP is mandatory")
    @Schema(example = "123456")
    Integer otp;

}
