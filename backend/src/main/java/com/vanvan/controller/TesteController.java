package com.vanvan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TesteController {

    
    @GetMapping("/perfil")
    public ResponseEntity<String> getPerfil() {
        return ResponseEntity.ok("Sucesso: Você acessou um endpoint protegido! O mecanismo de controle de acesso está funcionando.");
    }
}