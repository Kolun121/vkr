package ru.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.util.ObjectUtils;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.example.demo.domain.MunicipalityForecast;
import ru.example.demo.domain.Municipality;
import ru.example.demo.domain.PlannedProtectiveMeasure;
import ru.example.demo.domain.ProtectiveMeasure;
import ru.example.demo.helper.objects.PopulationInAnalyzedPeriod;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityForecastService;
import ru.example.demo.service.MunicipalityService;
import ru.example.demo.service.ProtectiveMeasureService;
import ru.example.demo.service.PlannedProtectiveMeasureService;


@Controller("adminMunicipalityForecastController")
@RequestMapping("/admin/municipalities")
public class MunicipalityForecastController {
    private static final String ADMIN_MUNICIPALITY_FORECAST_PATH = "admin/municipality_forecast";
    
    
    
    
    private final MunicipalityService municipalityService;
    private final MunicipalityForecastService municipalityForecastService;
    private final ProtectiveMeasureService protectiveMeasureService;
    private final PlannedProtectiveMeasureService plannedProtectiveMeasureService;
    
    public MunicipalityForecastController(
            MunicipalityService municipalityService,
            MunicipalityForecastService municipalityForecastService,
            ProtectiveMeasureService protectiveMeasureService,
            PlannedProtectiveMeasureService plannedProtectiveMeasureService
            ) 
    {
        this.municipalityService = municipalityService;
        this.municipalityForecastService = municipalityForecastService;
        this.protectiveMeasureService = protectiveMeasureService;
        this.plannedProtectiveMeasureService = plannedProtectiveMeasureService;
    }
    
    
    @GetMapping("{municipalityId}/forecast")
    public String getMunicipalityForecastCreateOrUpdatePage(@PathVariable Long municipalityId, Model model){
        PopulationInAnalyzedPeriod populationInAnalyzedPeriod = new PopulationInAnalyzedPeriod();
        Municipality municipality = municipalityService.findById(municipalityId);
        
        populationInAnalyzedPeriod.setPopulationInFifthYear(municipality.getCurrentPopulation());
        
        model.addAttribute("municipalityId", municipalityId);
        model.addAttribute("populationInAnalyzedPeriod", populationInAnalyzedPeriod);
                
        return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
    }
    

    @GetMapping("{municipalityId}/forecasts/new")
    public String municipalityForecastCreatePage(@PathVariable Long municipalityId, Model model){
        MunicipalityForecast municipalityForecast = new MunicipalityForecast();
        
        municipalityForecast.setMunicipality(municipalityService.findById(municipalityId));
        
        List<ProtectiveMeasure> protectiveMeasuresAll = protectiveMeasureService.findAll();
        List<PlannedProtectiveMeasure> plannedProtectiveMeasuresArr = new ArrayList<>();
        
        for (ProtectiveMeasure protectiveMeasure : protectiveMeasuresAll) {
            PlannedProtectiveMeasure plannedProtectiveMeasure = new PlannedProtectiveMeasure();
            
            plannedProtectiveMeasure.setMunicipalityForecast(municipalityForecast);
            plannedProtectiveMeasure.setAmountOfMeasures(0);
            
            plannedProtectiveMeasure.setProtectiveMeasure(protectiveMeasure);
            
           
            plannedProtectiveMeasuresArr.add(plannedProtectiveMeasure);
        }
        
        municipalityForecast.setPlannedProtectiveMeasures(plannedProtectiveMeasuresArr);
        
        model.addAttribute("municipalityForecast", municipalityForecast);
//        plannedProtectiveMeasureService.saveAll(plannedProtectiveMeasures);
//        MunicipalityForecast savedMunicipalityForecast = municipalityForecastService.save(municipalityForecast);

        return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
    }
    
    @PostMapping("{municipalityId}/forecast")
    public String newMunicipalityForecast(@PathVariable Long municipalityId,Model model, @Valid PopulationInAnalyzedPeriod populationInAnalyzedPeriod, BindingResult result){
        
        if (result.hasErrors()) {
            
            return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
        } else {
            
//            municipalityForecast.getPlannedProtectiveMeasures().stream().forEach(m -> m.setMunicipalityForecast(municipalityForecast));
//            
//            municipalityForecastService.save(municipalityForecast);

            return "redirect:/admin/municipalities/" + municipalityId + "/forecasts";
        }
    }
    
    @PostMapping("{municipalityId}/forecasts/{municipalityForecastId}")
    public String updateMunicipalityForecast(@PathVariable Long municipalityId, @PathVariable Long municipalityForecastId,Model model, @Valid MunicipalityForecast municipalityForecast, BindingResult result){
        if (result.hasErrors()) {
            
            return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
        } else {
            
            int municipalityCurrentPopulation = municipalityForecast.getMunicipality().getCurrentPopulation();
            
            double municipalityProjectedPopulation = (double) municipalityForecast.getProjectedPopulation();
            
            double averageHumanLifeCost = (double) municipalityForecast.getMunicipality().getFederalSubject().getAverageHumanLifeCost();
            System.out.println("Текущее население муниципалитета - " + municipalityCurrentPopulation);
            System.out.println("Прогнозное население муниципалитета - " + municipalityProjectedPopulation);
            System.out.println("Средняя статистическая стоимость жизни для субъекта - " + averageHumanLifeCost);
//            ------------------------Расчеты---------------------------------------



            AtomicReference<Double> probabilityOfDeathMunicipalityMSL = new AtomicReference<>();
            probabilityOfDeathMunicipalityMSL.set(Double.valueOf(0));

            AtomicReference<Double> riskOfDeathDeathMunicipalityMSL = new AtomicReference<>();
            riskOfDeathDeathMunicipalityMSL.set(Double.valueOf(0));
            
            AtomicReference<Double> probabilityOfDeathMunicipalityMMS = new AtomicReference<>();
            probabilityOfDeathMunicipalityMMS.set(Double.valueOf(0));

            AtomicReference<Double> riskOfDeathDeathMunicipalityMMS = new AtomicReference<>();
            riskOfDeathDeathMunicipalityMMS.set(Double.valueOf(0));
            
            municipalityForecast.getMunicipality().getWaterBodies()
                    .stream()
                    .forEach(waterBody -> 
                    {
                       
                        System.out.println("-----------------------------Расчеты рисков и вероятностей---------------------------------------");
                        System.out.println("Водный объект - " + waterBody.getTitle());
                        System.out.println("-----------------Места массового скопления-------------");
                        
//                        double probabilityOfDeathMunicipalityMSL = 0;
//                        double riskOfDeathDeathMunicipalityMSL = 0;
                        
//                        AtomicReference<Double> probabilityOfDeathMunicipalityMSL = new AtomicReference<>();
//                        probabilityOfDeathMunicipalityMSL.set(Double.valueOf(0));
//                        
//                        AtomicReference<Double> riskOfDeathDeathMunicipalityMSL = new AtomicReference<>();
//                        riskOfDeathDeathMunicipalityMSL.set(Double.valueOf(0));
                        
                        
                        waterBody.getCrowdedPlaces()
                                .stream()
                                .forEach(crowdedPlace ->
                                {
                                    System.out.println("МСЛ - " + crowdedPlace.getTitle());
                                    double probabilityOfDeath = (double) crowdedPlace.getDeathToll() / (double) municipalityCurrentPopulation;
                                    System.out.printf("Вероятность смерти - %.10f", probabilityOfDeath);
                                    System.out.println("");
                                    
                                    probabilityOfDeathMunicipalityMSL
                                            .set(probabilityOfDeathMunicipalityMSL.get() + probabilityOfDeath);
                                    
                                    double riskOfDeath = probabilityOfDeath * municipalityProjectedPopulation * averageHumanLifeCost;
                                    
                                    riskOfDeathDeathMunicipalityMSL
                                            .set(riskOfDeathDeathMunicipalityMSL.get() + riskOfDeath);
                                    
                                    System.out.printf("Риск смерти - %.10f", riskOfDeath);
                                    System.out.println("");
                                }
                                );
                        
                        System.out.println("");
                        System.out.println("Водный объект - " + waterBody.getTitle());
                        System.out.println("-----------------Места эксплуатации маломерных судов-------------");
                        waterBody.getSmallVesselsOperationPlaces()
                                .stream()
                                .forEach(smallVesselPlace ->
                                {
                                    System.out.println("ММС - " + smallVesselPlace.getTitle());
                                    double probabilityOfDeath = (double) smallVesselPlace.getDeathToll() / (double) municipalityCurrentPopulation;
                                    System.out.printf("Вероятность смерти - %.10f", probabilityOfDeath);
                                    System.out.println("");
                                    
                                    probabilityOfDeathMunicipalityMMS
                                            .set(probabilityOfDeathMunicipalityMMS.get() + probabilityOfDeath);
                                   
                                    
                                    double riskOfDeath = probabilityOfDeath * municipalityProjectedPopulation * averageHumanLifeCost;
            
                                    riskOfDeathDeathMunicipalityMMS
                                            .set(riskOfDeathDeathMunicipalityMMS.get() + riskOfDeath);
                                    
                                    
                                    System.out.printf("Риск смерти - %.10f", riskOfDeath);
                                    System.out.println("");
                                }
                                );
                    }
                    );
            
            System.out.println("");
                        
            System.out.printf("Вероятность смерти для муниципалитета МСЛ - %.10f", probabilityOfDeathMunicipalityMSL.get());
            System.out.println("");
            System.out.printf("Риск смерти для муниципалитета МСЛ - %.10f", riskOfDeathDeathMunicipalityMSL.get());
            
            System.out.println("");
            System.out.printf("Вероятность смерти для муниципалитета ММС - %.10f", probabilityOfDeathMunicipalityMMS.get());
            System.out.println("");
            System.out.printf("Риск смерти для муниципалитета ММС - %.10f", riskOfDeathDeathMunicipalityMMS.get());
            System.out.println("");
            
            
            
            double probabilityOfDeathMunicipality = probabilityOfDeathMunicipalityMSL.get() + probabilityOfDeathMunicipalityMMS.get();
            
            double riskOfDeathMunicipality = riskOfDeathDeathMunicipalityMMS.get() + riskOfDeathDeathMunicipalityMMS.get();
            
            System.out.println("");
            System.out.printf("Общая вероятность смерти для муниципалитета - %.10f", probabilityOfDeathMunicipality);
            System.out.println("");
            System.out.printf("Общий риск смерти для муниципалитета - %.10f", riskOfDeathMunicipality);
            System.out.println("");


            
//            ------------------------Конец Расчетов---------------------------------------
            municipalityForecastService.save(municipalityForecast);

            return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
        }
    }
    
    
    @GetMapping("{municipalityId}/forecasts/{municipalityForecastId}") 
    public String getMunicipalityForecastById(@PathVariable Long municipalityId, @PathVariable Long municipalityForecastId, Model model) {
        MunicipalityForecast municipalityForecast = municipalityForecastService.findById(municipalityForecastId);

//        List<ProtectiveMeasure> protectiveMeasuresAll = protectiveMeasureService.findAll();
//        List<PlannedProtectiveMeasure> plannedProtectiveMeasuresArr = new ArrayList<>();
//        
//        for (ProtectiveMeasure protectiveMeasure : protectiveMeasuresAll) {
//            PlannedProtectiveMeasure plannedProtectiveMeasure = new PlannedProtectiveMeasure();
//            
//            plannedProtectiveMeasure.setMunicipalityForecast(municipalityForecast);
//            plannedProtectiveMeasure.setAmountOfMeasures(0);
//            
//            plannedProtectiveMeasure.setProtectiveMeasure(protectiveMeasure);
//            protectiveMeasure.getPlannedProtectiveMeasure().add(plannedProtectiveMeasure);
//            
//           
//            plannedProtectiveMeasuresArr.add(plannedProtectiveMeasure);
//        }
//        
//        municipalityForecast.setPlannedProtectiveMeasures(plannedProtectiveMeasuresArr);
        
        model.addAttribute("municipalityForecast", municipalityForecast);
        model.addAttribute("municipalityId", municipalityId);
        
        return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
    }

    @DeleteMapping("{municipalityId}/forecasts/delete")
    @ResponseBody
    public void deleteMunicipalityForecasts(@RequestBody List<MunicipalityForecast> listMunicipalityForecasts){
        municipalityForecastService.deleteAll(listMunicipalityForecasts);
    }
}
