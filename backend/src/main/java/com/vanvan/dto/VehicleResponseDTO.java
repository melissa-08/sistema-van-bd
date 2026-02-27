package com.vanvan.dto;
import com.vanvan.model.Vehicle;
import java.util.UUID;

public record VehicleResponseDTO (
    UUID id,
    String plate,
    String model,
    Integer seatsQuantity,
    UUID driverId,
    String driverName
) {
    public static VehicleResponseDTO from(Vehicle vehicle) {
        return new VehicleResponseDTO(
            vehicle.getId(),
            vehicle.getPlate(),
            vehicle.getModel(),
            vehicle.getSeatsQuantity(),
            vehicle.getDriver().getId(),
            vehicle.getDriver().getName()
        );
    }
}
