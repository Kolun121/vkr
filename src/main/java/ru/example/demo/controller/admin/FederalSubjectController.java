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

import ru.example.demo.domain.FederalSubject;
import ru.example.demo.domain.Municipality;
import ru.example.demo.domain.enumeration.FederalSubjectType;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.FederalSubjectService;
import ru.example.demo.service.MunicipalityService;


@Controller("adminFederalSubjectController")
@RequestMapping("/admin/federal-subjects")
public class FederalSubjectController {
    private static final String ADMIN_FEDERAL_SUBJECT_PATH = "admin/federal_subject";
    
    
    
    
    private final FederalSubjectService federalSubjectService;
    private final MunicipalityService municipalityService;
    
    public FederalSubjectController(
            FederalSubjectService federalDistrictService,
            MunicipalityService municipalityService
            ) 
    {
        this.federalSubjectService = federalDistrictService;
        this.municipalityService = municipalityService;
    }
    
    
    @GetMapping
    public String getFederalSubjectsPage(){
        return ADMIN_FEDERAL_SUBJECT_PATH + "/listFederalSubjects";
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
    
    @GetMapping("{id}") 
    public String getFederalSubjectById(@PathVariable Long id, Model model) {
        FederalSubject federalSubject = federalSubjectService.findById(id);
     
        List<Municipality> availableMunicipalities = municipalityService.findAll().stream()
                                    .filter((Municipality municipality) -> {
                                        if(municipality.getFederalSubject()!= null){
                                            if(!municipality.getFederalSubject().getId().equals(id)){
                                                return false;
                                            }
                                        }
                                        return true;
                                    })
                                    .collect((Collectors.toList()));
       
        model.addAttribute("federalSubject", federalSubject);
        model.addAttribute("federalSubjectTypeValues", FederalSubjectType.values());
        model.addAttribute("availableMunicipalities", availableMunicipalities);
        
        return ADMIN_FEDERAL_SUBJECT_PATH + "/updateFederalSubject";
    }
    
    @PostMapping("{id}")
    public String updateFederalSubjectById(@PathVariable Long id, @Valid FederalSubject federalSubject, BindingResult result) {
        if (result.hasErrors()) {
            
            return ADMIN_FEDERAL_SUBJECT_PATH + "/updateFederalSubject";
        } else {
            federalSubject.setId(id);
            System.out.println(federalSubject.getFederalDistrict());
            List<Municipality> municipalitiesOfSubject = municipalityService.findAllByFederalSubjectId(id);
            
            for (Municipality municipality : federalSubject.getMunicipalities()) {
                
                if(municipalitiesOfSubject.contains(municipality)){
                    municipalitiesOfSubject.remove(municipality);
                }
                
                municipality.setFederalSubject(federalSubject);
            }
            
            if(!municipalitiesOfSubject.isEmpty()){
                for (Municipality municipality : municipalitiesOfSubject) {
                    municipality.setFederalSubject(null);
                }
                municipalityService.saveAll(municipalitiesOfSubject);
            }
             
            federalSubjectService.save(federalSubject);
            return "redirect:/admin/federal-subjects/" + id;
        }
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteFederalSubject(@RequestBody List<FederalSubject> listFederalDistricts){
        federalSubjectService.deleteAll(listFederalDistricts);
    }
}
