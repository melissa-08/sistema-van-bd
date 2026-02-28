package com.vanvan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "route_stops")
@Getter
@Setter
@NoArgsConstructor
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String city; // cidade

    @Column(name = "stop_location", nullable = false, length = 100)
    private String stopLocation; // local_parada

    @Column(name = "stop_order", nullable = false)
    private Integer stopOrder; // ordem_na_rota

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route; // id_rota

    @OneToMany(mappedBy = "boardingStop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelPrice> pricesAsBoarding = new ArrayList<>();

    @OneToMany(mappedBy = "dropOffStop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelPrice> pricesAsDropOff = new ArrayList<>();

    public RouteStop(String city, String stopLocation, Integer stopOrder, Route route) {
        this.city = city;
        this.stopLocation = stopLocation;
        this.stopOrder = stopOrder;
        this.route = route;
    }
}