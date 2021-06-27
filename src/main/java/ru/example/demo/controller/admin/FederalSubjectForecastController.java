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
import ru.example.demo.domain.ProtectiveMeasure;
import ru.example.demo.domain.SmallVesselOperationPlace;
import ru.example.demo.domain.WaterBody;
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
        
        if(federalSubject.getFederalSubjectForecast() != null){
            populationInAnalyzedPeriod.setPopulationInFirstYear(federalSubject.getFederalSubjectForecast().getPopulationInFirstYear());
            populationInAnalyzedPeriod.setPopulationInSecondYear(federalSubject.getFederalSubjectForecast().getPopulationInSecondYear());
            populationInAnalyzedPeriod.setPopulationInThirdYear(federalSubject.getFederalSubjectForecast().getPopulationInThirdYear());
            populationInAnalyzedPeriod.setPopulationInFourthYear(federalSubject.getFederalSubjectForecast().getPopulationInFourthYear());
        }
        
//        populationInAnalyzedPeriod.setPopulationInFifthYear(federalSubject.getCurrentPopulation());
//        
//        model.addAttribute("municipalityId", municipalityId);
//        model.addAttribute("populationInAnalyzedPeriod", populationInAnalyzedPeriod);
//        model.addAttribute("municipalityForecast", municipality.getMunicipalityForecast());
                
        return ADMIN_FEDERAL_SUBJECT_FORECAST_PATH + "/updateFederalSubjectForecast";
    }
    
    
    @PostMapping("{federalSubjectId}/forecast")
    public String updateFederalSubjectForecast(@PathVariable Long federalSubjectId,Model model, @Valid PopulationInAnalyzedPeriod populationInAnalyzedPeriod, BindingResult result){
        
        if (result.hasErrors()) {
            
            return ADMIN_FEDERAL_SUBJECT_FORECAST_PATH + "/updateFederalSubjectForecast";
        } else {
           

            return "redirect:/admin/federal-subjects/" + federalSubjectId + "/forecast";
        }
    }
    
}
