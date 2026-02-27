package com.vanvan.repository;

import com.vanvan.model.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RouteStopRepository extends JpaRepository<RouteStop, UUID> {
}