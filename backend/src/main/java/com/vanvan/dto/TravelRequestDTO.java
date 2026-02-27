package com.vanvan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vanvan.enums.TravelStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TravelRequestDTO(
        @NotNull @FutureOrPresent 
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime departureTime,
        
        @NotNull TravelStatus status,
        @NotNull UUID vehicleId,
        @NotNull UUID routeId,
        
        @NotEmpty @Valid List<TravelPriceDTO> prices
) {}