package ru.example.demo.helper.objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PopulationInAnalyzedPeriod {
    
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Integer populationInFirstYear;
    
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Integer populationInSecondYear;
    
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Integer populationInThirdYear;
    
    @Min(value = 1, message = "Значение должно быть больше нуля")
    @NotNull(message = "Значение должно быть заполнено")
    private Integer populationInFourthYear;
    
    private Integer populationInFifthYear;
    
    public float getPopulationDynamicsCoefficient(){
        int[] analyzedPeriodPopulation = new int[]{populationInFirstYear, populationInSecondYear, populationInThirdYear, populationInFourthYear, populationInFifthYear};
        
        float sumOfCoefficents = 0;
        for(int i = 0; i < analyzedPeriodPopulation.length - 1; i++){
            if(analyzedPeriodPopulation[i] == 0){
                continue;
            }
            sumOfCoefficents += (float) analyzedPeriodPopulation[i + 1] / (float) analyzedPeriodPopulation[i];
        }
        
        return sumOfCoefficents/4;
    }
}
