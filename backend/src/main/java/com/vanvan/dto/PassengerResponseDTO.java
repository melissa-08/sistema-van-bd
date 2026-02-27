package com.vanvan.dto;

import com.vanvan.model.Passenger;
import java.time.LocalDate;
import java.util.UUID;

public record PassengerResponseDTO(
        UUID id,
        String name,
        String email,
        String phone,
        String cpf,
        LocalDate birthDate
) {
    public static PassengerResponseDTO from(Passenger passenger) {
        return new PassengerResponseDTO(
                passenger.getId(),
                passenger.getName(),
                passenger.getEmail(),
                passenger.getPhone(),
                passenger.getCpf(),
                passenger.getBirthDate()
        );
    }
}