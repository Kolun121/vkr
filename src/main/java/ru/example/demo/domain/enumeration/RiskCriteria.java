package ru.example.demo.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RiskCriteria {
    INSIGNIFICANT(0, "Несущественный риск", "#00b050"),
    ACCEPTABLE(1, "Приемлемый риск", "#ffff00"),
    INCREASED(2, "Повышенный риск", "#c00000"),
    HIGH(3, "Высокий риск", "#ff0000");
    
    private final int intValue;
    private final String stringValue;
    private final String colorValue;
    
    RiskCriteria(int intValue, String stringValue, String colorValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.colorValue = colorValue;
    }

    @JsonValue
    public int getIntValue() {
        return intValue;
    }
    
    @JsonValue
    public String getStringValue() {
        return stringValue;
    }
    
    @JsonValue
    public String getColorValue() {
        return colorValue;
    }
}
