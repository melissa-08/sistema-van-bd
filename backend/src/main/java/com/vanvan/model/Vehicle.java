package com.vanvan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 7)
    private String plate;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(name = "seats_quantity", nullable = false)
    private Integer seatsQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false) // id_motorista
    private Driver driver;

    public Vehicle(String plate, String model, Integer seatsQuantity, Driver driver) {
        this.plate = plate;
        this.model = model;
        this.seatsQuantity = seatsQuantity;
        this.driver = driver;
    }
}
