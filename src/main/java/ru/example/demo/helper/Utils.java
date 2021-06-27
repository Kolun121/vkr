package ru.example.demo.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    public static double calculateProbabiltyOfDeath(int deathToll, int currentPopulation){
        BigDecimal dtBD = new BigDecimal(deathToll);
        BigDecimal cpBD = new BigDecimal(currentPopulation);

        return dtBD.divide(cpBD, 10, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double calculateRiskOfDeath(double probabilityOfDeath, double projectedPopulation, double averageHumanLifeCost){
        BigDecimal podBD = new BigDecimal(probabilityOfDeath);
        BigDecimal ppBD = new BigDecimal(projectedPopulation);
        BigDecimal ahlfBD = new BigDecimal(averageHumanLifeCost);
        
        return podBD.multiply(ppBD).multiply(ahlfBD).doubleValue();
    }
    
    public static double addUpDoubles(double d1, double d2){
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.add(d2BD).doubleValue();
    }
    
    public static double divideDoubles(double d1, double d2){
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.divide(d2BD, 4, RoundingMode.HALF_UP).doubleValue();
    }
    
}
