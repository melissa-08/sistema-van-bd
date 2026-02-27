package com.vanvan.repository;

import com.vanvan.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
    Administrator findByEmail(String email);
}