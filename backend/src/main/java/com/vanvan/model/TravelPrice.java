package com.vanvan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "travel_prices")
@Getter
@Setter
@NoArgsConstructor
public class TravelPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boarding_stop_id", nullable = false) // id_ponto_subida
    private RouteStop boardingStop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dropoff_stop_id", nullable = false) // id_ponto_descida
    private RouteStop dropOffStop;

    @Column(name = "price", nullable = false, precision = 10, scale = 2) // valor_trecho
    private BigDecimal price;

    public TravelPrice(RouteStop boardingStop, RouteStop dropOffStop, BigDecimal price, Travel travel) {
        this.boardingStop = boardingStop;
        this.dropOffStop = dropOffStop;
        this.price = price;
        this.travel = travel;
    }
}