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

import ru.example.demo.domain.FederalSubject;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.FederalSubjectService;


@Controller("adminFederalSubjectController")
@RequestMapping("/admin/federal-subjects")
public class FederalSubjectController {
    private static final String ADMIN_FEDERAL_SUBJECT_PATH = "admin/federal_subject";
    
    
    
    
    private final FederalSubjectService federalSubjectService;
    
    public FederalSubjectController(
            FederalSubjectService federalDistrictService
            ) 
    {
        this.federalSubjectService = federalDistrictService;
    }
    
    
    @GetMapping
    public String getFederalSubjectsPage(){
        return ADMIN_FEDERAL_SUBJECT_PATH + "/index";
    }
    
    @PostMapping
    @ResponseBody
    public Page<FederalSubject> listFederalSubjectsAjax(@RequestBody PagingRequest pagingRequest) {

        return federalSubjectService.findAllPagingRequest(pagingRequest);
    } 
    
    @PostMapping("/new")
    public @ResponseBody String newFederalSubject(Model model){
        FederalSubject newFederalSubject = new FederalSubject();
        
        FederalSubject savedFederalSubject = federalSubjectService.save(newFederalSubject);

        return savedFederalSubject.getId().toString();
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteFederalSubject(@RequestBody List<FederalSubject> listFederalDistricts){
        federalSubjectService.deleteAll(listFederalDistricts);
    }
}
