package com.vanvan.service;

import com.vanvan.dto.VehicleRequestDTO;
import com.vanvan.dto.VehicleResponseDTO;
import com.vanvan.model.Driver;
import com.vanvan.model.Vehicle;
import com.vanvan.repository.DriverRepository;
import com.vanvan.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public VehicleResponseDTO create(VehicleRequestDTO dto) {
        if (vehicleRepository.existsByPlate(dto.plate())) {
            throw new IllegalArgumentException("Já existe uma veículo cadastrado com esta placa.");
        }

        Driver driver = driverRepository.findById(dto.driverId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado."));

        Vehicle vehicle = new Vehicle(dto.plate(), dto.model(), dto.seatsQuantity(), driver);
        return VehicleResponseDTO.from(vehicleRepository.save(vehicle));
    }

    public List<VehicleResponseDTO> findAll() {
        return vehicleRepository.findAll().stream()
                .map(VehicleResponseDTO::from)
                .toList();
    }

    public VehicleResponseDTO findById(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));
        return VehicleResponseDTO.from(vehicle);
    }

    public VehicleResponseDTO update(UUID id, VehicleRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));

        if (!vehicle.getPlate().equals(dto.plate()) && vehicleRepository.existsByPlate(dto.plate())) {
            throw new IllegalArgumentException("Já existe outro veículo cadastrado com esta placa.");
        }

        Driver driver = driverRepository.findById(dto.driverId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado."));

        vehicle.setPlate(dto.plate());
        vehicle.setModel(dto.model());
        vehicle.setSeatsQuantity(dto.seatsQuantity());
        vehicle.setDriver(driver);

        return VehicleResponseDTO.from(vehicleRepository.save(vehicle));
    }

    public void delete(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));
        vehicleRepository.delete(vehicle);
    }
}