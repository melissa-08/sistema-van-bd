package com.vanvan.repository;

import com.vanvan.model.Passenger;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}