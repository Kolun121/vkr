package ru.example.demo.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    public static double calculateProbabiltyOfDeath(int deathToll, int currentPopulation){
        if(currentPopulation == 0){
            return 0;
        }
        
        BigDecimal dtBD = new BigDecimal(deathToll);
        BigDecimal cpBD = new BigDecimal(currentPopulation);

        return dtBD.divide(cpBD, 10, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double calculateRiskOfDeath(double probabilityOfDeath, double projectedPopulation, double averageHumanLifeCost){
        BigDecimal podBD = new BigDecimal(probabilityOfDeath);
        BigDecimal ppBD = new BigDecimal(projectedPopulation);
        BigDecimal ahlfBD = new BigDecimal(averageHumanLifeCost);
        
        return podBD.multiply(ppBD).multiply(ahlfBD).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double addUpDoubles(double d1, double d2){
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.add(d2BD).doubleValue();
    }
    
    public static double addUpRiskDoubles(double d1, double d2){
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.add(d2BD).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double multiplyDoubles(double d1, double d2){
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.multiply(d2BD).doubleValue();
    }
       
    public static double divideDoubles(double d1, double d2){
        if(d2 == 0){
            return 0;
        }
        
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.divide(d2BD, 4, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double subtractDoubles(double d1, double d2){
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.subtract(d2BD).doubleValue();
    }
    public static double subtractRiskDoubles(double d1, double d2){
        BigDecimal d1BD = new BigDecimal(d1);
        BigDecimal d2BD = new BigDecimal(d2);
        
        return d1BD.subtract(d2BD).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    
}
