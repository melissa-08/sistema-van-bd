package com.vanvan.repository;

import com.vanvan.enums.RegistrationStatus;
import com.vanvan.model.Driver;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    boolean existsByCnh(String cnh);

    Page<Driver> findByRegistrationStatus(RegistrationStatus status, Pageable pageable);
}