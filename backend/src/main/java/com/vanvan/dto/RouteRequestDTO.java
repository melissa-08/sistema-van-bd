package com.vanvan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record RouteRequestDTO(
        @NotBlank String name,
        @NotEmpty(message = "A rota deve ter pelo menos um ponto") 
        @Valid List<RouteStopDTO> stops
) {}