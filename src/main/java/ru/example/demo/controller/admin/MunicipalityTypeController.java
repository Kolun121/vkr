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

import ru.example.demo.domain.MunicipalityType;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityTypeService;


@Controller("adminMunicipalityTypeController")
@RequestMapping("/admin/municipality-types")
public class MunicipalityTypeController {
    private static final String ADMIN_MUNICIPALITYTYPE_PATH = "admin/municipality_type";
    
    private final MunicipalityTypeService municipalityTypeService;
    
    public MunicipalityTypeController(
            MunicipalityTypeService municipalityTypeService
            ) 
    {
        this.municipalityTypeService = municipalityTypeService;
    }
    
    
    @GetMapping
    public String listMunicipalityTypes(Model model){
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.MUNICIPALITY_TYPES));
        return ADMIN_MUNICIPALITYTYPE_PATH + "/listMunicipalityTypes";
    }
    
    @PostMapping
    @ResponseBody
    public Page<MunicipalityType> listMunicipalityTypesAjax(@RequestBody PagingRequest pagingRequest) {

        return municipalityTypeService.findAllPagingRequest(pagingRequest);
    } 
    
    @PostMapping("/update")
    @ResponseBody
    public String updateMunicipalityType(@RequestBody List<MunicipalityType> listMunicipalityTypes){

        municipalityTypeService.saveAll(listMunicipalityTypes);

        return "redirect:/admin/listMunicipalityTypes";
    }
    
    @PostMapping("/new")
    public @ResponseBody String newMunicipalityType(Model model){
        MunicipalityType newMunicipalityType = new MunicipalityType();
        
        MunicipalityType savedMunicipalityType = municipalityTypeService.save(newMunicipalityType);

        return savedMunicipalityType.getId().toString();
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteMunicipalityType(@RequestBody List<MunicipalityType> listMunicipalities){
        municipalityTypeService.deleteAll(listMunicipalities);
    }
}
