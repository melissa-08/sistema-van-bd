package com.vanvan.service;

import com.vanvan.dto.RouteRequestDTO;
import com.vanvan.dto.RouteResponseDTO;
import com.vanvan.model.Route;
import com.vanvan.model.RouteStop;
import com.vanvan.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteResponseDTO create(RouteRequestDTO dto) {
        Route route = new Route(dto.name());
        
        //adiciona os pontos vinculando-os à rota
        dto.stops().forEach(stopDto -> {
            RouteStop stop = new RouteStop(stopDto.city(), stopDto.stopLocation(), stopDto.stopOrder(), route);
            route.addStop(stop);
        });

        return RouteResponseDTO.from(routeRepository.save(route));
    }

    public List<RouteResponseDTO> findAll() {
        return routeRepository.findAll().stream()
                .map(RouteResponseDTO::from)
                .toList();
    }

    public RouteResponseDTO findById(UUID id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rota não encontrada."));
        return RouteResponseDTO.from(route);
    }

    public RouteResponseDTO update(UUID id, RouteRequestDTO dto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rota não encontrada."));

        route.setName(dto.name());
        
        //apaga os pontos antigos e insere os novos
        route.getStops().clear();
        dto.stops().forEach(stopDto -> {
            RouteStop stop = new RouteStop(stopDto.city(), stopDto.stopLocation(), stopDto.stopOrder(), route);
            route.addStop(stop);
        });

        return RouteResponseDTO.from(routeRepository.save(route));
    }

    public void delete(UUID id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rota não encontrada."));
        routeRepository.delete(route); // Isso deleta a rota E todos os pontos amarrados a ela
    }
}