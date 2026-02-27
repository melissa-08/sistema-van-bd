package com.vanvan.dto;

import com.vanvan.model.TravelPrice;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TravelPriceDTO(
        @NotNull UUID boardingStopId,
        @NotNull UUID dropOffStopId,
        @NotNull @Positive BigDecimal price
) {
    public static TravelPriceDTO from(TravelPrice tp) {
        return new TravelPriceDTO(
                tp.getBoardingStop().getId(),
                tp.getDropOffStop().getId(),
                tp.getPrice()
        );
    }
}