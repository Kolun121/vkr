package ru.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.example.demo.domain.Municipality;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityForecastService;
import ru.example.demo.service.MunicipalityService;

@Controller
@RequestMapping("/municipalities")
public class MunicipalityController {
    private static final String MUNICIPALITY_PATH = "user/municipality";
    
    private final MunicipalityService municipalityService;
    private final MunicipalityForecastService municipalityForecastService;
    
    public MunicipalityController(
            MunicipalityService municipalityService,
            MunicipalityForecastService municipalityForecastService            
    ) 
    {
        this.municipalityService = municipalityService;
        this.municipalityForecastService = municipalityForecastService;
    }
    
    
    @GetMapping
    public String getMunicipalitiesPage(Model model){        
        return MUNICIPALITY_PATH + "/listMunicipalities";
    }
    
    @PostMapping
    @ResponseBody
    public Page<Municipality> listMunicipalitiesAjax(@RequestBody PagingRequest pagingRequest) {
        return municipalityService.findAllPagingRequest(pagingRequest);
    } 
}
