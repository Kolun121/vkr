package ru.example.demo.helper.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MapData {
    private String mapId;
    private String title;
    private String color;
    private String risk;
    private Double riskOfDeath;
    private Double acceptableRiskOfDeath;
    private Double costOfMeasures;
    private Double riskOfDeathMeasures;
    private Double effectivenessOfMeasures;
    
}
