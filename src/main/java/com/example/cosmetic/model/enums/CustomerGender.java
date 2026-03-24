package com.example.cosmetic.model.enums;

public enum CustomerGender {
    MALE("Nam"), 
    FEMALE("Nữ"), 
    OTHER("Khác");

    private String displayName;

    CustomerGender(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}