package com.vanvan.enums;

public enum RegistrationStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private String status;

    RegistrationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
}
