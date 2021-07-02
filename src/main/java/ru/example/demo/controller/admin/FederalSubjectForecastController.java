package ru.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;


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
import ru.example.demo.domain.CrowdedPlace;
import ru.example.demo.domain.MunicipalityForecast;
import ru.example.demo.domain.Municipality;
import ru.example.demo.domain.AppliedProtectiveMeasure;
import ru.example.demo.domain.FederalSubject;
import ru.example.demo.domain.FederalSubjectForecast;
import ru.example.demo.domain.ProtectiveMeasure;
import ru.example.demo.domain.SmallVesselOperationPlace;
import ru.example.demo.domain.WaterBody;
import ru.example.demo.domain.enumeration.RiskCriteria;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.Utils;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.objects.PopulationInAnalyzedPeriod;
import ru.example.demo.service.MunicipalityService;
import ru.example.demo.service.FederalSubjectService;
import ru.example.demo.service.FederalSubjectForecastService;


@Controller("adminFederalSubjectForecastController")
@RequestMapping("/admin/federal-subjects")
public class FederalSubjectForecastController {
    private static final String ADMIN_FEDERAL_SUBJECT_FORECAST_PATH = "admin/federal_subject_forecast";
    
    private final MunicipalityService municipalityService;
    private final FederalSubjectService federalSubjectService;
    private final FederalSubjectForecastService federalSubjectForecastService;
    

    public FederalSubjectForecastController(
            MunicipalityService municipalityService,
            FederalSubjectService federalSubjectService,
            FederalSubjectForecastService federalSubjectForecastService
            ) 
    {
        this.municipalityService = municipalityService;
        this.federalSubjectService = federalSubjectService;
        this.federalSubjectForecastService = federalSubjectForecastService;
    }
    
    
    @GetMapping("{subjectId}/forecast")
    public String getFederalSubjectForecastCreatePage(@PathVariable Long subjectId, Model model){
        FederalSubject federalSubject = federalSubjectService.findById(subjectId);
        
        long [] ids = new long[]{subjectId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.FEDERAL_SUBJECT, ids));
        
        
        PopulationInAnalyzedPeriod populationInAnalyzedPeriod = new PopulationInAnalyzedPeriod();
     
        
        if(federalSubject.getFederalSubjectForecast()!= null){
            populationInAnalyzedPeriod.setPopulationInFirstYear(federalSubject.getFederalSubjectForecast().getPopulationInFirstYear());
            populationInAnalyzedPeriod.setPopulationInSecondYear(federalSubject.getFederalSubjectForecast().getPopulationInSecondYear());
            populationInAnalyzedPeriod.setPopulationInThirdYear(federalSubject.getFederalSubjectForecast().getPopulationInThirdYear());
            populationInAnalyzedPeriod.setPopulationInFourthYear(federalSubject.getFederalSubjectForecast().getPopulationInFourthYear());
        }
        
        populationInAnalyzedPeriod.setPopulationInFifthYear(federalSubject.getCurrentPopulation());
        
        model.addAttribute("federalSubjectId", subjectId);
        model.addAttribute("federalSubjectForecast", federalSubject.getFederalSubjectForecast());
        model.addAttribute("populationInAnalyzedPeriod", populationInAnalyzedPeriod);
        
        return ADMIN_FEDERAL_SUBJECT_FORECAST_PATH + "/updateFederalSubjectForecast";
    }
    
    
    @PostMapping("{federalSubjectId}/forecast")
    public String updateFederalSubjectForecast(@PathVariable Long federalSubjectId,Model model, @Valid PopulationInAnalyzedPeriod populationInAnalyzedPeriod, BindingResult result){
        
        FederalSubject federalSubject = federalSubjectService.findById(federalSubjectId);
        FederalSubjectForecast federalSubjectForecast = federalSubject.getFederalSubjectForecast();
        
        //коэффицент прироста численности населения
        federalSubjectForecast.setPopulationDynamicsCoefficient(populationInAnalyzedPeriod.getPopulationDynamicsCoefficient());

        //прогнозируемая численность населения
        int federalSubjectProjectedPopulation = 
            (int)(
             federalSubjectForecast.getPopulationDynamicsCoefficient() 
                            * 
             federalSubject.getCurrentPopulation()
            );
        federalSubjectForecast.setProjectedPopulation(federalSubjectProjectedPopulation);
        
        int abverageHumanLifeCostSubject = federalSubject.getAverageHumanLifeCost();
        
        //Допустимая вероятность гибели
        double acceptableProbabilityOfDeath = federalSubject.getAcceptableProbabilityOfDeath();
        
        // Допустимый риск смерти для субъекта
        double acceptableRiskOfDeathFederalSubject = Utils.calculateRiskOfDeath(acceptableProbabilityOfDeath, federalSubjectProjectedPopulation, abverageHumanLifeCostSubject);
        federalSubjectForecast.setAcceptableRiskOfDeathFederalSubject(acceptableRiskOfDeathFederalSubject);
        
        
        //Дополнительные защитные меры
        double additionalProtectiveMeasuresCost = 0;
        //Риск смерти для субъекта
        double riskOfDeathFederalSubject = 0;
        
        //Расчет дополнительных защитных мер и риска смерти для субъекта
        for(Municipality m : federalSubject.getMunicipalities()){
            MunicipalityForecast municipalityForecast;
            
            if(m.getMunicipalityForecast() == null){
                continue;
            }
            
            if(m.getMunicipalityForecast().getRiskOfDeathMunicipality() == null){
                continue;
            }
            
            municipalityForecast = m.getMunicipalityForecast();
            
            double riskOfDeathMunicipality = municipalityForecast.getRiskOfDeathMunicipality();
            riskOfDeathFederalSubject = Utils.addUpRiskDoubles(riskOfDeathFederalSubject, riskOfDeathMunicipality);
            
            for(AppliedProtectiveMeasure measure: municipalityForecast.getAppliedProtectiveMeasure()){
                double efficentCoef = measure.getEfficencyCoefficent();
                double measureCost = measure.getProtectivemeasureCost();
                double costXMeasure = Utils.multiplyDoubles(efficentCoef, measureCost);
                
                additionalProtectiveMeasuresCost = Utils.addUpDoubles(additionalProtectiveMeasuresCost, costXMeasure);
            }
        }
        
        federalSubjectForecast.setCostOfAdditionalProtectiveMeasures(additionalProtectiveMeasuresCost);
        federalSubjectForecast.setRiskOfDeathFederalSubject(riskOfDeathFederalSubject);
        
        //Прогнозное значение риска гибели людей на водных объектах территории субъекта Российской Федерации с учетом проведения превентивных мероприятий:
        double riskOfDeathWithTakenMeasuresFederalSubject = Utils.subtractRiskDoubles(riskOfDeathFederalSubject, additionalProtectiveMeasuresCost);
        federalSubjectForecast.setRiskOfDeathWithTakenMeasuresFederalSubject(riskOfDeathWithTakenMeasuresFederalSubject);
        
        //Прогнозное значение эффективности принятых мероприятий
        double effectivenessOfTakenMeasures = Utils.divideDoubles(riskOfDeathWithTakenMeasuresFederalSubject, acceptableRiskOfDeathFederalSubject);
        federalSubjectForecast.setEffectivenessOfTakenMeasures(effectivenessOfTakenMeasures);
        
        //Значения критерия индивидуального риска
        double riskSubstract = Utils.subtractDoubles(riskOfDeathFederalSubject, acceptableRiskOfDeathFederalSubject);
        RiskCriteria riskCriteria;
        if(riskSubstract < 0){
            riskCriteria = RiskCriteria.INSIGNIFICANT;
        } else if(riskSubstract == 0) {
            riskCriteria = RiskCriteria.ACCEPTABLE;
        } else if(riskSubstract > 0 && riskSubstract < 10){
            riskCriteria = RiskCriteria.INCREASED;
        } else {
            riskCriteria = RiskCriteria.HIGH;
        }
        
        federalSubjectForecast.setRiskCriteria(riskCriteria);
        
        
        federalSubjectForecastService.save(federalSubjectForecast);
        return "redirect:/admin/federal-subjects/" + federalSubjectId + "/forecast";
        
    }
    
}
