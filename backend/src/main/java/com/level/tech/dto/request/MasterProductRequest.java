package com.level.tech.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterProductRequest {

    @NotNull(message = "Category is mandatory")
    private String category;

    @NotNull(message = "Product is mandatory")
    private String product;

    @NotNull(message = "Model is mandatory")
    private String model;

    @PositiveOrZero(message = "Tax must be zero or a positive value")
    private Double tax;

    private String hsnAcs;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

}
