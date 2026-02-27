package com.vanvan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vanvan.model.Travel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TravelResponseDTO(
        UUID id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime departureTime,
        String status,
        String driverName,
        String vanPlate,
        String routeName,
        List<TravelPriceDTO> prices
) {
    public static TravelResponseDTO from(Travel travel) {
        return new TravelResponseDTO(
                travel.getId(),
                travel.getDepartureTime(),
                travel.getStatus().getDescription(),
                travel.getDriver().getName(),
                travel.getVehicle().getPlate(),
                travel.getRoute().getName(),
                travel.getPrices().stream().map(TravelPriceDTO::from).toList()
        );
    }
}