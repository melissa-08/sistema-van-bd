package com.vanvan.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Immutable
@Table(name = "v_ranking_motoristas")
@Getter
@NoArgsConstructor
public class DriverRankingView {
    @Id
    @Column(name = "driver_id")
    private UUID driverId;
    
    @Column(name = "driver_name")
    private String driverName;
    
    @Column(name = "average_rating")
    private Double averageRating;
    
    @Column(name = "total_feedbacks")
    private Long totalFeedbacks;
}