package com.vanvan.dto;

import com.vanvan.model.Route;
import java.util.List;
import java.util.UUID;

public record RouteResponseDTO(
        UUID id,
        String name,
        List<RouteStopDTO> stops
) {
    public static RouteResponseDTO from(Route route) {
        return new RouteResponseDTO(
                route.getId(),
                route.getName(),
                route.getStops().stream().map(RouteStopDTO::from).toList()
        );
    }
}