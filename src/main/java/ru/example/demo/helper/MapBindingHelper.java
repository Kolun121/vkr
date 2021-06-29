package ru.example.demo.helper;

import java.util.ArrayList;
import java.util.List;
import ru.example.demo.domain.FederalSubjectForecast;
import ru.example.demo.helper.objects.MapData;

public class MapBindingHelper {
    private static final String[] MAP_VALUES = new String[]{"RU-MOW", "RU-SPE", "RU-NEN", "RU-YAR", "RU-CHE", "RU-ULY", "RU-TYU", "RU-TUL", "RU-SVE", "RU-RYA", "RU-ORL", "RU-OMS", "RU-NGR", "RU-LIP", "RU-KRS", "RU-KGN", "RU-KGD", "RU-IVA", "RU-BRY", "RU-AST", "RU-KHA", "RU-CE", "RU-UD", "RU-SE", "RU-MO", "RU-KR", "RU-KL", "RU-IN", "RU-AL", "RU-BA", "RU-AD", "RU-CR", "RU-SEV", "RU-KO", "RU-KIR", "RU-PNZ", "RU-TAM", "RU-MUR", "RU-LEN", "RU-VLG", "RU-KOS", "RU-PSK", "RU-ARK", "RU-YAN", "RU-CHU", "RU-YEV", "RU-TY", "RU-SAK", "RU-AMU", "RU-BU", "RU-KK", "RU-KEM", "RU-NVS", "RU-ALT", "RU-DA", "RU-STA", "RU-KB", "RU-KC", "RU-KDA", "RU-ROS", "RU-SAM", "RU-TA", "RU-ME", "RU-CU", "RU-NIZ", "RU-VLA", "RU-MOS", "RU-KLU", "RU-BEL", "RU-ZAB", "RU-PRI", "RU-KAM", "RU-MAG", "RU-SA", "RU-KYA", "RU-ORE", "RU-SAR", "RU-VGG", "RU-VOR", "RU-SMO", "RU-TVE", "RU-PER", "RU-KHM", "RU-TOM", "RU-IRK"};
    
    public static List<MapData> getMapData(List<FederalSubjectForecast> forecasts){
        List<MapData> mDs = new ArrayList<>();
        
        for (FederalSubjectForecast f : forecasts) {
            MapData md = new MapData();
            
            md.setMapId(f.getFederalSubject().getMapIdValue());
            md.setTitle(f.getFederalSubject().getTitle());
            md.setColor(f.getRiskCriteria().getColorValue());
            md.setRisk(f.getRiskCriteria().getStringValue());
            
            md.setRiskOfDeath(f.getRiskOfDeathFederalSubject());
            md.setAcceptableRiskOfDeath(f.getAcceptableRiskOfDeathFederalSubject());
            md.setCostOfMeasures(f.getCostOfAdditionalProtectiveMeasures());
            md.setRiskOfDeathMeasures(f.getRiskOfDeathWithTakenMeasuresFederalSubject());
            md.setEffectivenessOfMeasures(f.getEffectivenessOfTakenMeasures());
            
            mDs.add(md);
        }
        
        return mDs;
    }
}
