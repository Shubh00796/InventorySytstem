package com.inventory.management.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BinDTO {
    private Long id;

    @NotBlank(message = "Location must not be blank")
    private String location;

    @Positive(message = "Capacity must be greater than zero")
    private double capacity;

    @Min(value = 0, message = "Fill level cannot be negative")
    private double currentFillLevel;
}