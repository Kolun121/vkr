package ru.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    
    
    @GetMapping("{municipalityId}/forecasts")
    public String getMunicipalityForecastsPage(@PathVariable Long municipalityId, Model model){
        model.addAttribute("municipalityId", municipalityId);
        return ADMIN_MUNICIPALITY_FORECAST_PATH + "/listMunicipalityForecasts";
    }
    
    @PostMapping("{municipalityId}/forecasts")
    @ResponseBody
    public Page<MunicipalityForecast> listMunicipalityForecastsAjax(@PathVariable Long municipalityId, @RequestBody PagingRequest pagingRequest) {

        return municipalityForecastService.findAllByMunicipalityIdPagingRequest(municipalityId, pagingRequest);
    } 
    
//    @PostMapping("{municipalityId}/forecasts/new")
//    public @ResponseBody String newMunicipality(Model model){
//        MunicipalityForecast newMunicipalityForecast = new MunicipalityForecast();
//        
//        MunicipalityForecast municipalityForecast = municipalityForecastService.save(newMunicipalityForecast);
//
//        return municipalityForecast.getId().toString();
//    }
    
    @PostMapping("{municipalityId}/forecasts/new")
    public @ResponseBody String newMunicipalityForecast(@PathVariable Long municipalityId,Model model){
        MunicipalityForecast municipalityForecast = new MunicipalityForecast();
        
        municipalityForecast.setMunicipality(municipalityService.findById(municipalityId));
        
        List<ProtectiveMeasure> protectiveMeasuresAll = protectiveMeasureService.findAll();
        List<PlannedProtectiveMeasure> plannedProtectiveMeasuresArr = new ArrayList<>();
        
        for (ProtectiveMeasure protectiveMeasure : protectiveMeasuresAll) {
            PlannedProtectiveMeasure plannedProtectiveMeasure = new PlannedProtectiveMeasure();
            
            plannedProtectiveMeasure.setMunicipalityForecast(municipalityForecast);
            plannedProtectiveMeasure.setAmountOfMeasures(0);
            
            plannedProtectiveMeasure.setProtectiveMeasure(protectiveMeasure);
            protectiveMeasure.getPlannedProtectiveMeasure().add(plannedProtectiveMeasure);
            
           
            plannedProtectiveMeasuresArr.add(plannedProtectiveMeasure);
        }
        
        municipalityForecast.setPlannedProtectiveMeasures(plannedProtectiveMeasuresArr);
        
//        plannedProtectiveMeasureService.saveAll(plannedProtectiveMeasures);
        MunicipalityForecast savedMunicipalityForecast = municipalityForecastService.save(municipalityForecast);

        return savedMunicipalityForecast.getId().toString();
    }
    
    @PostMapping("{municipalityId}/forecasts/{municipalityForecastId}")
    public String updateMunicipalityForecast(@PathVariable Long municipalityId, @PathVariable Long municipalityForecastId,Model model, @Valid MunicipalityForecast municipalityForecast, BindingResult result){
        if (result.hasErrors()) {
            
            return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
        } else {
            
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
    
//    @PostMapping("{municipalityId}/forecasts/{municipalityForecastId}") 
//    public String updateMunicipalityForecastById(@PathVariable Long municipalityId, @PathVariable Long municipalityForecastId, @Valid MunicipalityForecast municipalityForecast, BindingResult result) {
//        if (result.hasErrors()) {
//            
//            return ADMIN_MUNICIPALITY_FORECAST_PATH + "/createOrUpdateMunicipalityForecast";
//        } else {
//            municipalityForecast.setId(municipalityForecastId);
//            
//            municipalityForecastService.save(municipalityForecast);
//
//            return "redirect:/admin/municipalities/" + municipalityId + "/forecasts/" + municipalityForecastId;
//        }
//    }
    
    @DeleteMapping("{municipalityId}/forecasts/delete")
    @ResponseBody
    public void deleteMunicipalityForecasts(@RequestBody List<MunicipalityForecast> listMunicipalityForecasts){
        municipalityForecastService.deleteAll(listMunicipalityForecasts);
    }
}
