package com.vanvan.repository;

import com.vanvan.model.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TravelRepository extends JpaRepository<Travel, UUID> {}