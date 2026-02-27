package com.vanvan.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "v_relatorio_financeiro_viagem")
@Getter
@NoArgsConstructor
public class FinancialReportView {
    @Id
    @Column(name = "travel_id")
    private UUID travelId;
    
    @Column(name = "route_name")
    private String routeName;
    
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    
    @Column(name = "total_passengers")
    private Integer totalPassengers;
    
    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;
}