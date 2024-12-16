package com.level.tech.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    @NotEmpty(message = "Role name is mandatory")
    @NotNull(message = "Role name is mandatory")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Role should not contain numbers or special characters")
    @Size(min = 2, max = 30)
    @Schema(example = "AGENT")
    private String name;

}
