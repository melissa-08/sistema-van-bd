package com.vanvan.model;

import com.vanvan.enums.TravelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "travels")
@Getter
@Setter
@NoArgsConstructor
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime; // horario_saida

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TravelStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    // cascade salva os preços dos trechos junto com a viagem
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelPrice> prices = new ArrayList<>();

    public Travel(LocalDateTime departureTime, TravelStatus status, Vehicle vehicle, Route route) {
        this.departureTime = departureTime;
        this.status = status;
        this.vehicle = vehicle;
        this.route = route;
    }

    public void addPrice(TravelPrice price) {
        prices.add(price);
        price.setTravel(this);
    }
}