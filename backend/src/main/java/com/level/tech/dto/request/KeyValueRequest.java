package com.level.tech.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueRequest {

    @NotNull(message = "Id is mandatory")
    private Long id;

    @NotNull(message = "Name is mandatory")
    private String name;

}
