package com.vanvan.controller;

import com.vanvan.dto.*;
import com.vanvan.enums.RegistrationStatus;
import com.vanvan.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    
//endpoints de motoristas
    @GetMapping("/drivers")
    public ResponseEntity<Page<DriverAdminResponseDTO>> listDrivers(
            @RequestParam(required = false) RegistrationStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(adminService.listDrivers(status, pageable));
    }

    @PutMapping("/drivers/{id}/status")
    public ResponseEntity<DriverAdminResponseDTO> updateDriverStatus(
            @PathVariable UUID id,
            @Valid @RequestBody DriverStatusUpdateDTO dto) {
        return ResponseEntity.ok(adminService.updateDriverStatus(id, dto));
    }

    @PutMapping("/drivers/{id}")
    public ResponseEntity<DriverAdminResponseDTO> updateDriver(
            @PathVariable UUID id,
            @Valid @RequestBody DriverUpdateDTO dto) {
        return ResponseEntity.ok(adminService.updateDriver(id, dto));
    }

    @DeleteMapping("/drivers/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable UUID id) {
        adminService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    // endpoints de passageiros

    @GetMapping("/clients")
    public ResponseEntity<Page<PassengerResponseDTO>> listClients(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(adminService.listClients(pageable));
    }

    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@Valid @RequestBody RegisterRequestDTO dto) {
        try {
            return ResponseEntity.ok(adminService.createClient(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> updateClient(
            @PathVariable UUID id,
            @Valid @RequestBody PassengerUpdateDTO dto) {
        try {
            return ResponseEntity.ok(adminService.updateClient(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        try {
            adminService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}