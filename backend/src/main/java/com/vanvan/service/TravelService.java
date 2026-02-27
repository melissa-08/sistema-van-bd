package com.vanvan.service;

import com.vanvan.dto.TravelRequestDTO;
import com.vanvan.dto.TravelResponseDTO;
import com.vanvan.enums.TravelStatus;
import com.vanvan.model.*;
import com.vanvan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final VehicleRepository vehicleRepository;
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;

    public TravelResponseDTO create(TravelRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));
        Route route = routeRepository.findById(dto.routeId())
                .orElseThrow(() -> new IllegalArgumentException("Rota não encontrada."));

        Travel travel = new Travel(dto.departureTime(), dto.status(), vehicle, route);

        dto.prices().forEach(priceDto -> {
            RouteStop boarding = routeStopRepository.findById(priceDto.boardingStopId())
                    .orElseThrow(() -> new IllegalArgumentException("Ponto de subida não encontrado."));
            RouteStop dropOff = routeStopRepository.findById(priceDto.dropOffStopId())
                    .orElseThrow(() -> new IllegalArgumentException("Ponto de descida não encontrado."));
            
            if (!boarding.getRoute().getId().equals(route.getId()) || 
                !dropOff.getRoute().getId().equals(route.getId())) {
                throw new IllegalArgumentException("Os pontos de subida e descida devem pertencer à rota selecionada para esta viagem.");
            }

            if (boarding.getStopOrder() >= dropOff.getStopOrder()) {
                throw new IllegalArgumentException("Erro: O ponto de descida (" + dropOff.getCity() + ") deve vir depois do ponto de subida (" + boarding.getCity() + ") na rota.");
            }

            travel.addPrice(new TravelPrice(boarding, dropOff, priceDto.price(), travel));
        });

        return TravelResponseDTO.from(travelRepository.save(travel));
    }

    public List<TravelResponseDTO> findAll() {
        return travelRepository.findAll().stream()
                .map(TravelResponseDTO::from)
                .toList();
    }

    public TravelResponseDTO update(UUID id, TravelRequestDTO dto) {
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada."));
        Vehicle vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));
        Route route = routeRepository.findById(dto.routeId())
                .orElseThrow(() -> new IllegalArgumentException("Rota não encontrada."));

        travel.setDepartureTime(dto.departureTime());
        travel.setStatus(dto.status());
        travel.setVehicle(vehicle); 
        travel.setRoute(route);

        travel.getPrices().clear();

        dto.prices().forEach(priceDto -> {
            RouteStop boarding = routeStopRepository.findById(priceDto.boardingStopId())
                    .orElseThrow(() -> new IllegalArgumentException("Ponto de subida não encontrado."));
            RouteStop dropOff = routeStopRepository.findById(priceDto.dropOffStopId())
                    .orElseThrow(() -> new IllegalArgumentException("Ponto de descida não encontrado."));
            
            if (!boarding.getRoute().getId().equals(route.getId()) || 
                !dropOff.getRoute().getId().equals(route.getId())) {
                throw new IllegalArgumentException("Os pontos de subida e descida devem pertencer à rota selecionada para esta viagem.");
            }

            if (boarding.getStopOrder() >= dropOff.getStopOrder()) {
                throw new IllegalArgumentException("Erro: O ponto de descida (" + dropOff.getCity() + ") deve vir depois do ponto de subida (" + boarding.getCity() + ") na rota.");
            }

            travel.addPrice(new TravelPrice(boarding, dropOff, priceDto.price(), travel));
        });

        return TravelResponseDTO.from(travelRepository.save(travel));
    }

    //viagem não tem delete, apenas cancelamento
    public void cancel(UUID id) {
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada."));

        travel.setStatus(TravelStatus.CANCELED);
        travelRepository.save(travel);
    }
}