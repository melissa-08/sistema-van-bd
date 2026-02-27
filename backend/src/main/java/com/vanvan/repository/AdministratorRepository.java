package com.vanvan.repository;

import com.vanvan.model.Administrator;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
}