package com.vanvan.dto;

import com.vanvan.enums.RegistrationStatus;
import jakarta.validation.constraints.NotNull;

public record DriverStatusUpdateDTO(
    @NotNull
    RegistrationStatus status,
    String rejectionReason
) {}