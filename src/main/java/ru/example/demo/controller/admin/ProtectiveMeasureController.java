package ru.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.ModelAndView;

import ru.example.demo.domain.ProtectiveMeasure;
import ru.example.demo.domain.enumeration.WaterBodyType;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.ProtectiveMeasureService;

@Controller("adminProtectiveMeasureController")
@RequestMapping("/admin/protective-measures")
public class ProtectiveMeasureController {
    private static final String ADMIN_PROTECTIVE_MEASURE_PATH = "admin/protective_measure";
    
    
    
    
    private final ProtectiveMeasureService protectiveMeasureService;
    
    public ProtectiveMeasureController(
            ProtectiveMeasureService protectiveMeasureService
            ) 
    {
        this.protectiveMeasureService = protectiveMeasureService;
    }
    
    
    @GetMapping
    public String getProtectiveMeasuresPage(){
        return ADMIN_PROTECTIVE_MEASURE_PATH + "/listProtectiveMeasures";
    }
    
    @PostMapping
    @ResponseBody
    public Page<ProtectiveMeasure> listProtectiveMeasuresAjax(@RequestBody PagingRequest pagingRequest) {

        return protectiveMeasureService.findAllPagingRequest(pagingRequest);
    } 
    
    @PostMapping("/new")
    public @ResponseBody String newProtectiveMeasure(Model model){
        ProtectiveMeasure newProtectiveMeasure = new ProtectiveMeasure();
        
        ProtectiveMeasure savedProtectiveMeasure = protectiveMeasureService.save(newProtectiveMeasure);

        return savedProtectiveMeasure.getId().toString();
    }
    
   @GetMapping("{id}") 
    public String getProtectiveMeasureById(@PathVariable Long id, Model model) {
        ProtectiveMeasure protectiveMeasure = protectiveMeasureService.findById(id);
       
        model.addAttribute("protectiveMeasure", protectiveMeasure);
        model.addAttribute("waterBodyTypeValues", WaterBodyType.values());
        
        return ADMIN_PROTECTIVE_MEASURE_PATH + "/updateProtectiveMeasure";
    }
    
    @PostMapping("{id}")
    public String updateProtectiveMeasureById(@PathVariable Long id, @Valid ProtectiveMeasure protectiveMeasure, BindingResult result) {
        if (result.hasErrors()) {
            
            return ADMIN_PROTECTIVE_MEASURE_PATH + "/updateProtectiveMeasure";
        } else {
            protectiveMeasure.setId(id);

            protectiveMeasureService.save(protectiveMeasure);
            return "redirect:/admin/protective-measures/" + id;
        }
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteMunicipality(@RequestBody List<ProtectiveMeasure> listProtectiveMeasures){
        protectiveMeasureService.deleteAll(listProtectiveMeasures);
    }
}
