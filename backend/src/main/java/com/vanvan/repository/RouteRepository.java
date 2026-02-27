package com.vanvan.repository;

import com.vanvan.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {
}