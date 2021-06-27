package ru.example.demo.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfCrowdedPlace {
    BEACH("Пляж"),
    WATER_BRIDGE("Водная переправа или наплавной мост"),
    ICE_CROSSING("Ледовая переправа"),
    ICE_FISHING_PLACE("Места массового выхода людей на лед для подледного лова рыбы");

    private final String value;
    
    TypeOfCrowdedPlace(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
