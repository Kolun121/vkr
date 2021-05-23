package ru.example.demo.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FederalSubjectType {
    REPUBLIC("Республика"),
    TERRITORY("Край"),
    REGION("Регион"),
    CITY_OF_FEDERAL_IMPORTANCE("Город федерального значения"),
    AUTONOMOUS_REGION("Автономная область"),
    AUTONOMOUS_AREA("Автономный округ");

    private final String value;
    
    FederalSubjectType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
