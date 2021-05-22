package ru.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
import ru.example.demo.domain.Municipality;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityService;


@Controller("adminMunicipalityController")
@RequestMapping("/admin/municipalities")
public class MunicipalityController {
    private static final String ADMIN_MUNICIPALITY_PATH = "admin/municipality";
    
    
    
    
    private final MunicipalityService municipalityService;
    
    public MunicipalityController(
            MunicipalityService municipalityService
            ) 
    {
        this.municipalityService = municipalityService;
    }
    
    
    @GetMapping
    public String getMunicipalitiesPage(){
        return ADMIN_MUNICIPALITY_PATH + "/index";
    }
    
    @PostMapping
    @ResponseBody
    public Page<Municipality> listMunicipalitiesAjax(@RequestBody PagingRequest pagingRequest) {

        return municipalityService.findAllPagingRequest(pagingRequest);
    } 
    
    @PostMapping("/new")
    public @ResponseBody String newMunicipality(Model model){
        Municipality newMunicipality = new Municipality();
        
        Municipality savedMunicipality = municipalityService.save(newMunicipality);

        return savedMunicipality.getId().toString();
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteMunicipality(@RequestBody List<Municipality> listMunicipalities){
        municipalityService.deleteAll(listMunicipalities);
    }
}
