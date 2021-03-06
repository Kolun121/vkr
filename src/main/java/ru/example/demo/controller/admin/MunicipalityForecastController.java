package ru.example.demo.controller.admin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import ru.example.demo.domain.CrowdedPlace;
import ru.example.demo.domain.MunicipalityForecast;
import ru.example.demo.domain.Municipality;
import ru.example.demo.domain.AppliedProtectiveMeasure;
import ru.example.demo.domain.ProtectiveMeasure;
import ru.example.demo.domain.SmallVesselOperationPlace;
import ru.example.demo.domain.WaterBody;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.Utils;
import ru.example.demo.helper.objects.Breadcrumb;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.objects.PopulationInAnalyzedPeriod;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityForecastService;
import ru.example.demo.service.MunicipalityService;
import ru.example.demo.service.ProtectiveMeasureService;
import ru.example.demo.service.AppliedProtectiveMeasureService;


@Controller("adminMunicipalityForecastController")
@RequestMapping("/admin/municipalities")
public class MunicipalityForecastController {
    private static final String ADMIN_MUNICIPALITY_FORECAST_PATH = "admin/municipality_forecast";
    
    private final MunicipalityService municipalityService;
    private final MunicipalityForecastService municipalityForecastService;
    private final AppliedProtectiveMeasureService appliedProtectiveMeasureService;
    

    public MunicipalityForecastController(
            MunicipalityService municipalityService,
            MunicipalityForecastService municipalityForecastService,
            AppliedProtectiveMeasureService appliedProtectiveMeasureService
            ) 
    {
        this.municipalityService = municipalityService;
        this.municipalityForecastService = municipalityForecastService;
        this.appliedProtectiveMeasureService = appliedProtectiveMeasureService;
    }
    
    
    @GetMapping("{municipalityId}/forecast")
    public String getMunicipalityForecastCreatePage(@PathVariable Long municipalityId, Model model){
        Municipality municipality = municipalityService.findById(municipalityId);
        
        long [] ids = new long[]{municipalityId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.MUNICIPALITY_FORECAST, ids));
        
        
        PopulationInAnalyzedPeriod populationInAnalyzedPeriod = new PopulationInAnalyzedPeriod();
        
        if(municipality.getMunicipalityForecast() != null){
            populationInAnalyzedPeriod.setPopulationInFirstYear(municipality.getMunicipalityForecast().getPopulationInFirstYear());
            populationInAnalyzedPeriod.setPopulationInSecondYear(municipality.getMunicipalityForecast().getPopulationInSecondYear());
            populationInAnalyzedPeriod.setPopulationInThirdYear(municipality.getMunicipalityForecast().getPopulationInThirdYear());
            populationInAnalyzedPeriod.setPopulationInFourthYear(municipality.getMunicipalityForecast().getPopulationInFourthYear());
        }
        
        populationInAnalyzedPeriod.setPopulationInFifthYear(municipality.getCurrentPopulation());
        
        model.addAttribute("municipalityId", municipalityId);
        model.addAttribute("populationInAnalyzedPeriod", populationInAnalyzedPeriod);
        model.addAttribute("municipalityForecast", municipality.getMunicipalityForecast());
                
        return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
    }
    
    
    @PostMapping("{municipalityId}/forecast")
    public String createOrUpdateMunicipalityForecast(@PathVariable Long municipalityId,Model model, @Valid PopulationInAnalyzedPeriod populationInAnalyzedPeriod, BindingResult result){
        
        if (result.hasErrors()) {
            
            return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
        } else {
           Municipality municipality = municipalityService.findById(municipalityId);
           MunicipalityForecast municipalityForecast;
           
           if(municipality.getMunicipalityForecast() != null){
               municipalityForecast = municipality.getMunicipalityForecast();
           } else {
               municipalityForecast = new MunicipalityForecast();
               
               municipalityForecast.setMunicipality(municipality);
               municipality.setMunicipalityForecast(municipalityForecast);
           }
           
        
           
            municipalityForecast.setPopulationInFirstYear(populationInAnalyzedPeriod.getPopulationInFirstYear());
            municipalityForecast.setPopulationInSecondYear(populationInAnalyzedPeriod.getPopulationInSecondYear());
            municipalityForecast.setPopulationInThirdYear(populationInAnalyzedPeriod.getPopulationInThirdYear());
            municipalityForecast.setPopulationInFourthYear(populationInAnalyzedPeriod.getPopulationInFourthYear());

            //?????????????? ?????????????????? ????????????????????????????
            int municipalityCurrentPopulation = municipality.getCurrentPopulation();
            
            municipalityForecast.setPopulationInFifthYear(municipalityCurrentPopulation);

            //???????????????????? ???????????????? ?????????????????????? ??????????????????
            municipalityForecast.setPopulationDynamicsCoefficient(populationInAnalyzedPeriod.getPopulationDynamicsCoefficient());

            //???????????????????????????? ?????????????????????? ??????????????????
            int municipalityProjectedPopulation = 
                    (int)(
                     populationInAnalyzedPeriod.getPopulationDynamicsCoefficient() 
                                                  * 
                     municipality.getCurrentPopulation()
                    );

            municipalityForecast.setProjectedPopulation(municipalityProjectedPopulation);

            //?????????????? ???????? ?????????? ?????? ????????????????????????????
            double averageHumanLifeCostMunicipality = (double) municipality.getAverageHumanLifeCost();
           
            //?????????????????????? ???????????? ?????? ???????? ?????????????????? ??????????????????
            double probabilityOfDeathMunicipalityMSL = 0;
            
            //???????? ???????????? ?????? ???????? ?????????????????? ??????????????????
            double riskOfDeathDeathMunicipalityMSL = 0;
            
            //?????????????????????? ???????????? ?????? ???????? ???????????????????????? ??????????
            double probabilityOfDeathMunicipalityMMS = 0;

            //???????? ???????????? ?????? ???????? ???????????????????????? ??????????
            double riskOfDeathDeathMunicipalityMMS = 0;
            
            //?????????????????????? ???????????????? ???????? ?? ???????????? ???????????????? ????????????????????????????
            List<AppliedProtectiveMeasure> appliedProtectiveMeasures = new ArrayList<>();
            
            //???????????? ?????????????? ????????????????????????????
            List<WaterBody> waterBodies = municipality.getWaterBodies();
            
            for(var waterBody: waterBodies){
                List<CrowdedPlace> crowdedPlaces = waterBody.getCrowdedPlaces();
                List<SmallVesselOperationPlace> smallVesselOperationPlaces = waterBody.getSmallVesselsOperationPlaces();
                
                List<CrowdedPlace> crowdedPlacesWithoutMeasures = waterBody.getCrowdedPlaces()
                        .stream()
                        .filter(cp -> cp.getProtectiveMeasure() == null)
                        .collect(Collectors.toList());
                
                List<SmallVesselOperationPlace> smallVesselWithoutMeasures = waterBody.getSmallVesselsOperationPlaces()
                        .stream()
                        .filter(cp -> cp.getProtectiveMeasure() == null)
                        .collect(Collectors.toList());
                
                
                //---------------------?????????? ?????????????????? ?????????????????? ??????????---------------------------------------------
                for(var crowdedPlace: crowdedPlaces){
                    System.out.println("?????? - " + crowdedPlace.getTitle());
                    
                    double probabilityOfDeath = Utils.calculateProbabiltyOfDeath(crowdedPlace.getDeathToll(), municipalityCurrentPopulation);

                    //?????????????????? ?? ?????????? ?????????????????????? ???????????? ??????
                    probabilityOfDeathMunicipalityMSL = Utils.addUpDoubles(probabilityOfDeathMunicipalityMSL, probabilityOfDeath);
                    
                    double riskOfDeath = Utils.calculateRiskOfDeath(probabilityOfDeath, municipalityProjectedPopulation, averageHumanLifeCostMunicipality);

                    //?????????????????? ?? ???????????? ?????????? ???????????? ??????
                    riskOfDeathDeathMunicipalityMSL = Utils.addUpRiskDoubles(riskOfDeathDeathMunicipalityMSL, riskOfDeath);
                    
                    System.out.printf("???????? ???????????? - %.10f", riskOfDeath);
                    System.out.println("");
                    
                    if(crowdedPlace.getProtectiveMeasure() == null){continue;}
                    
                    ProtectiveMeasure protectiveMeasure = crowdedPlace.getProtectiveMeasure();
                    //???????????? ?????????????????????? ?????????????????????????? ???????????????? ??????
                    double coefficent;
                    
                    AppliedProtectiveMeasure appliedProtectiveMeasure = new AppliedProtectiveMeasure();
                    appliedProtectiveMeasure.setProtectiveMeasure(protectiveMeasure);
                    appliedProtectiveMeasure.setMunicipalityForecast(municipalityForecast);
                    
                    appliedProtectiveMeasures.add(appliedProtectiveMeasure);
                    
                    if(crowdedPlacesWithoutMeasures.isEmpty()) {
                        continue;
                    }
                    
                    CrowdedPlace crowdedPlaceComparedTo;
                    Optional<CrowdedPlace> cpOp = 
                    crowdedPlacesWithoutMeasures
                        .stream()
                        .filter(cp -> {
                            
                            return 
                                cp.getTypeOfCrowdedPlace().getValue().equals(protectiveMeasure.getDesignatedFor().getValue());
                        })
                        .findFirst();
                    
                    if(cpOp.isPresent()){
                        crowdedPlaceComparedTo = cpOp.get();
                    }else {
                        crowdedPlaceComparedTo = crowdedPlacesWithoutMeasures
                        .stream()
                        .findFirst()
                        .get();
                    }
                    
                    double divident = crowdedPlaceComparedTo.getDeathToll() * averageHumanLifeCostMunicipality;
                    double divider = crowdedPlace.getDeathToll() * averageHumanLifeCostMunicipality + crowdedPlace.getProtectiveMeasureCost();
                    
                    coefficent = Utils.divideDoubles(divident, divider);
                    
                    appliedProtectiveMeasure.setEfficencyCoefficent(coefficent);
                    appliedProtectiveMeasure.setProtectivemeasureCost(crowdedPlace.getProtectiveMeasureCost());
                    
                }
                //---------------------?????????? ?????????????????? ?????????????????? ??????????---------------------------------------------
                
                
                //---------------------?????????? ???????????????????????? ??????????---------------------------------------------
                for(var smallVesselPlace: smallVesselOperationPlaces){
                    System.out.println("?????? - " + smallVesselPlace.getTitle());
                    
                    double probabilityOfDeath = Utils.calculateProbabiltyOfDeath(smallVesselPlace.getDeathToll(), municipalityCurrentPopulation);
                    System.out.printf("?????????????????????? ???????????? - %.10f", probabilityOfDeath);
                    System.out.println("");

                    //?????????????????? ?? ?????????? ?????????????????????? ???????????? ??????
                    probabilityOfDeathMunicipalityMMS = Utils.addUpDoubles(probabilityOfDeathMunicipalityMMS, probabilityOfDeath);
                    
                    double riskOfDeath = Utils.calculateRiskOfDeath(probabilityOfDeath, municipalityProjectedPopulation, averageHumanLifeCostMunicipality);

                    //?????????????????? ?? ???????????? ?????????? ???????????? ??????
                    riskOfDeathDeathMunicipalityMMS = Utils.addUpRiskDoubles(riskOfDeathDeathMunicipalityMMS, riskOfDeath);

                    System.out.println("???????? ???????????? - %.10f" + riskOfDeath);
                    System.out.println("");
                    
                    if(smallVesselPlace.getProtectiveMeasure() == null){continue;}
                    
                    ProtectiveMeasure protectiveMeasure = smallVesselPlace.getProtectiveMeasure();
                    //???????????? ?????????????????????? ?????????????????????????? ???????????????? ??????
                    double coefficent;
                    
                    AppliedProtectiveMeasure appliedProtectiveMeasure = new AppliedProtectiveMeasure();
                    appliedProtectiveMeasure.setProtectiveMeasure(protectiveMeasure);
                    appliedProtectiveMeasure.setMunicipalityForecast(municipalityForecast);
                    
                    appliedProtectiveMeasures.add(appliedProtectiveMeasure);
                    
                    if(smallVesselWithoutMeasures.isEmpty()) {
                        continue;
                    }
                    
                    SmallVesselOperationPlace smallVesselComparedTo;
                    
                    smallVesselComparedTo = smallVesselWithoutMeasures
                    .stream()
                    .findFirst()
                    .get();
                  
                    
                    double divident = smallVesselComparedTo.getDeathToll() * averageHumanLifeCostMunicipality;
                    double divider = smallVesselComparedTo.getDeathToll() * averageHumanLifeCostMunicipality + smallVesselComparedTo.getProtectiveMeasureCost();
                    
                    coefficent = Utils.divideDoubles(divident, divider);
    
                    appliedProtectiveMeasure.setEfficencyCoefficent(coefficent);
                    appliedProtectiveMeasure.setProtectivemeasureCost(smallVesselComparedTo.getProtectiveMeasureCost());
                }
                //---------------------?????????? ???????????????????????? ??????????---------------------------------------------
                
            }
            
            //?????????????????????? ???????????? ?????? ?????? ?? ??????
            municipalityForecast.setProbabilityOfDeathMMSMunicipality(probabilityOfDeathMunicipalityMMS);
            municipalityForecast.setProbabilityOfDeathMSLMunicipality(probabilityOfDeathMunicipalityMSL);
            
            //?????????? ???????????? ?????? ?????? ?? ??????
            municipalityForecast.setRiskOfDeathMMSMunicipality(riskOfDeathDeathMunicipalityMMS);
            municipalityForecast.setRiskOfDeathMSLMunicipality(riskOfDeathDeathMunicipalityMSL);
            
            System.out.println("probabilityOfDeathMunicipalityMMS - " + probabilityOfDeathMunicipalityMMS);
            System.out.println("probabilityOfDeathMunicipalityMSL - " + probabilityOfDeathMunicipalityMSL);
            
            System.out.println("riskOfDeathDeathMunicipalityMMS - " + riskOfDeathDeathMunicipalityMMS);
            System.out.println("riskOfDeathDeathMunicipalityMSL - " + riskOfDeathDeathMunicipalityMSL);
            
            
            //?????????? ?????????????????????? ???????????? ?????? ????????????????????????????
            double probabilityOfDeathMunicipality = probabilityOfDeathMunicipalityMSL + probabilityOfDeathMunicipalityMMS;
            municipalityForecast.setProbabilityOfDeathMunicipality(probabilityOfDeathMunicipality);
            
            //?????????? ???????? ???????????? ?????? ????????????????????????????
            double riskOfDeathMunicipality = Utils.addUpRiskDoubles(riskOfDeathDeathMunicipalityMMS, riskOfDeathDeathMunicipalityMSL);
            municipalityForecast.setRiskOfDeathMunicipality(riskOfDeathMunicipality);
           
            //?????????? ????????????????
            
            if(municipalityForecast.getId() != null){
                List<AppliedProtectiveMeasure> list = appliedProtectiveMeasureService.findAllByMunicipalityForecastId(municipalityForecast.getId());

                appliedProtectiveMeasureService.deleteAll(list);
            }
            municipalityForecast.setAppliedProtectiveMeasure(appliedProtectiveMeasures);
            municipalityForecastService.save(municipalityForecast);

            return "redirect:/admin/municipalities/" + municipalityId + "/forecast";
        }
    }
    
}
