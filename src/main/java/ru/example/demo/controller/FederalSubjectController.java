package ru.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.example.demo.domain.FederalSubject;
import ru.example.demo.domain.FederalSubjectForecast;
import ru.example.demo.domain.Municipality;
import ru.example.demo.domain.MunicipalityForecast;
import ru.example.demo.domain.enumeration.FederalSubjectType;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.FederalSubjectForecastService;
import ru.example.demo.service.FederalSubjectService;
import ru.example.demo.service.MunicipalityService;


@Controller
@RequestMapping("/federal-subjects")
public class FederalSubjectController {
    private static final String FEDERAL_SUBJECT_PATH = "user/federal_subject";
   
    private final FederalSubjectService federalSubjectService;
    private final FederalSubjectForecastService federalSubjectForecastService;
    
    public FederalSubjectController(
            FederalSubjectService federalDistrictService,
            FederalSubjectForecastService federalSubjectForecastService            
    ) 
    {
        this.federalSubjectService = federalDistrictService;
        this.federalSubjectForecastService = federalSubjectForecastService;
    }
    
    
    @GetMapping
    public String getFederalSubjectsPage(Model model){
        return FEDERAL_SUBJECT_PATH + "/listFederalSubjects";
    }
    
    @PostMapping
    @ResponseBody
    public Page<FederalSubject> listFederalSubjectsAjax(@RequestBody PagingRequest pagingRequest) {
        Page<FederalSubject> temp = federalSubjectService.findAllPagingRequest(pagingRequest);
        return temp;
    }
}
