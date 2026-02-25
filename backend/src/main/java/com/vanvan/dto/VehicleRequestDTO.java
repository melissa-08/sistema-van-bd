package com.vanvan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record VehicleRequestDTO (
    @NotBlank
    @Size(min = 7, max = 7, message = "A placa deve conter exatamente 7 caracteres")
    String plate,

    @NotBlank
    String model,

    @NotNull
    @Min(value = 1, message = "A van deve ter pelo menos 1 assento")
    Integer seatsQuantity,

    @NotNull
    UUID driverId
) {
}
