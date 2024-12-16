package com.level.tech.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @Pattern(regexp = "^[\\p{L} ]+$", message = "Name should only contain letters and spaces")
    @NotEmpty(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    @Schema(example = "John")
    private String name;

    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    @Size(max = 254, message = "Email should not exceed 254 characters")
    @Schema(example = "johncena@gmail.com")
    private String email;

    @Schema(example = "+91")
    private String phoneCode;

    @Pattern(regexp = "^\\d{10,15}$", message = "Phone No should contain 10 to 15 digits")
    private String phoneNo;

    @Past(message = "Date of birth must be a past date")
    @Schema(example = "1995-08-15")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    @Schema(example = "User@123")
    private String password;

    @NotNull(message = "Terms and Conditions is mandatory")
    private Boolean termsAndConditions;
}
