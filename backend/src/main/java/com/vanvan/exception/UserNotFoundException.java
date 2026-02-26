package com.vanvan.exception;

import com.vanvan.enums.UserRole;

import java.util.UUID;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UserRole role, UUID id) {
        super(role.getRole().toUpperCase() + " não encontrado com ID " + id);
    }

}