package com.vanvan.dto;

import com.vanvan.model.RouteStop;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RouteStopDTO(
        UUID id,
        @NotBlank String city,
        @NotBlank String stopLocation,
        @NotNull Integer stopOrder
) {
    public static RouteStopDTO from(RouteStop stop) {
        return new RouteStopDTO(stop.getId(), stop.getCity(), stop.getStopLocation(), stop.getStopOrder());
    }
}