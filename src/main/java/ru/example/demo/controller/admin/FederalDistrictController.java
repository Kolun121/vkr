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

import ru.example.demo.domain.FederalDistrict;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.FederalDistrictService;


@Controller("adminFederalDistrictController")
@RequestMapping("/admin/federal-districts")
public class FederalDistrictController {
    private static final String ADMIN_FEDERAL_DISTRICT_PATH = "admin/federal_district";
    
    
    
    
    private final FederalDistrictService federalDistrictService;
    
    public FederalDistrictController(
            FederalDistrictService federalDistrictService
            ) 
    {
        this.federalDistrictService = federalDistrictService;
    }
    
    
    @GetMapping
    public String getFederalDistrictsPage(){
        return ADMIN_FEDERAL_DISTRICT_PATH + "/index";
    }
    
    @PostMapping
    @ResponseBody
    public Page<FederalDistrict> listMunicipalitiesAjax(@RequestBody PagingRequest pagingRequest) {

        return federalDistrictService.findAllPagingRequest(pagingRequest);
    } 
    
    @PostMapping("/new")
    public @ResponseBody String newMunicipality(Model model){
        FederalDistrict newFederalDistrict = new FederalDistrict();
        
        FederalDistrict savedFederalDistrict = federalDistrictService.save(newFederalDistrict);

        return savedFederalDistrict.getId().toString();
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteFederalDistrict(@RequestBody List<FederalDistrict> listFederalDistricts){
        federalDistrictService.deleteAll(listFederalDistricts);
    }
}
