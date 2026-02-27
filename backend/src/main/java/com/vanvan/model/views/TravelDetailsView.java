package com.vanvan.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "v_detalhes_viagem")
@Getter
@NoArgsConstructor
public class TravelDetailsView {
    @Id
    @Column(name = "travel_id")
    private UUID travelId;
    
    @Column(name = "driver_name")
    private String driverName;
    
    @Column(name = "vehicle_model")
    private String vehicleModel;
    
    @Column(name = "vehicle_plate")
    private String vehiclePlate;
    
    @Column(name = "route_name")
    private String routeName;
    
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    
    @Column(name = "status")
    private String status;
}