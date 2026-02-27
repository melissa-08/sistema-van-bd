package com.vanvan.controller;

import com.vanvan.dto.TravelRequestDTO;
import com.vanvan.dto.TravelResponseDTO;
import com.vanvan.service.TravelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/travels")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TravelRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(travelService.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TravelResponseDTO>> findAll() {
        return ResponseEntity.ok(travelService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody TravelRequestDTO dto) {
        try {
            return ResponseEntity.ok(travelService.update(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable UUID id) {
        try {
            travelService.cancel(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}