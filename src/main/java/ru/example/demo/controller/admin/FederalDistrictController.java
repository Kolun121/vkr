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

import ru.example.demo.domain.FederalDistrict;
import ru.example.demo.domain.FederalSubject;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.FederalDistrictService;
import ru.example.demo.service.FederalSubjectService;


@Controller("adminFederalDistrictController")
@RequestMapping("/admin/federal-districts")
public class FederalDistrictController {
    private static final String ADMIN_FEDERAL_DISTRICT_PATH = "admin/federal_district";
    
    
    
    
    private final FederalDistrictService federalDistrictService;
    private final FederalSubjectService federalSubjectService;
    
    public FederalDistrictController(
            FederalDistrictService federalDistrictService,
            FederalSubjectService federalSubjectService
            ) 
    {
        this.federalDistrictService = federalDistrictService;
        this.federalSubjectService = federalSubjectService;
    }
    
    
    @GetMapping
    public String getFederalDistrictsPage(){
        return ADMIN_FEDERAL_DISTRICT_PATH + "/index";
    }
    
    @PostMapping
    @ResponseBody
    public Page<FederalDistrict> listFederalDistrictsAjax(@RequestBody PagingRequest pagingRequest) {

        return federalDistrictService.findAllPagingRequest(pagingRequest);
    } 
    
    @PostMapping("/new")
    public @ResponseBody String newFederalDistrict(Model model){
        FederalDistrict newFederalDistrict = new FederalDistrict();
        
        FederalDistrict savedFederalDistrict = federalDistrictService.save(newFederalDistrict);

        return savedFederalDistrict.getId().toString();
    }
    
    @GetMapping("{id}") 
    public String getFederalDistrictById(@PathVariable Long id, Model model) {
        FederalDistrict federalDistrict = federalDistrictService.findById(id);
        
        List<FederalSubject> availableFederalSubjects = federalSubjectService.findAll().stream()
                                    .filter((FederalSubject federalSubject) -> {
                                        if(federalSubject.getFederalDistrict()!= null){
                                            if(!federalSubject.getFederalDistrict().getId().equals(id)){
                                                return false;
                                            }
                                        }
                                        return true;
                                    })
                                    .collect((Collectors.toList()));

        model.addAttribute("federalDistrict", federalDistrict);
        model.addAttribute("availableFederalSubjects", availableFederalSubjects);
        return ADMIN_FEDERAL_DISTRICT_PATH + "/updateFederalDistrict";
    }
    
    @PostMapping("{id}")
    public String updateFederalDistrictById(@PathVariable Long id, @Valid FederalDistrict federalDistrict, BindingResult result) {
        if (result.hasErrors()) {
            return ADMIN_FEDERAL_DISTRICT_PATH + "/updateFederalDistrict";
        } else {
            federalDistrict.setId(id);
                        
            List<FederalSubject> subjectsOfDistrict = federalSubjectService.findAllByFederalDistrictId(id);
            
            for (FederalSubject federalSubject : federalDistrict.getFederalSubjects()) {
                
                if(subjectsOfDistrict.contains(federalSubject)){
                    subjectsOfDistrict.remove(federalSubject);
                }
                
                federalSubject.setFederalDistrict(federalDistrict);

            }
            
            
            if(!subjectsOfDistrict.isEmpty()){
                for (FederalSubject federalSubject : subjectsOfDistrict) {
                    federalSubject.setFederalDistrict(null);
                }
                federalSubjectService.saveAll(subjectsOfDistrict);
            }

            federalDistrictService.save(federalDistrict);
            return "redirect:/admin/federal-districts/" + id;
        }
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteFederalDistrict(@RequestBody List<FederalDistrict> listFederalDistricts){
        federalDistrictService.deleteAll(listFederalDistricts);
    }
}
