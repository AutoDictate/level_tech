package com.level.tech.dto.request;

import com.level.tech.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotEmpty(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Name should only contain letters and spaces")
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
