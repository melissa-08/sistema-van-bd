package com.vanvan.enums;

public enum TravelStatus {
    SCHEDULED("Agendada"),
    IN_PROGRESS("Em Andamento"),
    COMPLETED("Concluída"),
    CANCELED("Cancelada");

    private final String description;

    TravelStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}