package ru.example.demo.controller.admin;

import java.util.List;
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
import ru.example.demo.domain.Municipality;
import ru.example.demo.domain.MunicipalityForecast;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.objects.Breadcrumb;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityForecastService;
import ru.example.demo.service.MunicipalityService;


@Controller("adminMunicipalityController")
@RequestMapping("/admin/municipalities")
public class MunicipalityController {
    private static final String ADMIN_MUNICIPALITY_PATH = "admin/municipality";
    
    
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
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.MUNICIPALITIES));
        
        return ADMIN_MUNICIPALITY_PATH + "/listMunicipalities";
    }
    
    @PostMapping
    @ResponseBody
    public Page<Municipality> listMunicipalitiesAjax(@RequestBody PagingRequest pagingRequest) {
        return municipalityService.findAllPagingRequest(pagingRequest);
    } 
    
    
    @GetMapping("{id}") 
    public String getMunicipalityById(@PathVariable Long id, Model model) {
        Municipality municipality = municipalityService.findById(id);
        
        long [] ids = new long[]{id};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.MUNICIPALITY, ids));
        
        model.addAttribute("municipality", municipality);
        
        return ADMIN_MUNICIPALITY_PATH + "/updateMunicipality";
    }
    
    @GetMapping("/new")
    public String municipalityCreatePage(Model model){
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.MUNICIPALITY_CREATE));
        model.addAttribute("municipality", new Municipality());
        return ADMIN_MUNICIPALITY_PATH + "/createMunicipality";
    }
    
    @PostMapping("/new") 
    public String createMunicipality(Model model, @Valid Municipality municipality, BindingResult result) {
        if (result.hasErrors()) {
            
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.MUNICIPALITY_CREATE));
            model.addAttribute("municipality", municipality);
        
            return ADMIN_MUNICIPALITY_PATH + "/createMunicipality";
        } else {            
            MunicipalityForecast municipalityForecast = new MunicipalityForecast();

            municipality.setMunicipalityForecast(municipalityForecast);
            municipalityForecast.setMunicipality(municipality);

            Municipality savedMunicipality = municipalityService.save(municipality);

            return "redirect:/admin/municipalities/" + savedMunicipality.getId().toString();
        }
    }
    
    @PostMapping("{id}")
    public String updateMunicipalityById(Model model, @PathVariable Long id, @Valid Municipality municipality, BindingResult result) {
        if (result.hasErrors()) {
            
            long [] ids = new long[]{id};
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.MUNICIPALITY, ids));

            model.addAttribute("municipality", municipality);

            return ADMIN_MUNICIPALITY_PATH + "/updateMunicipality";
        } else {
            municipality.setId(id);
            
            MunicipalityForecast municipalityForecast = municipalityForecastService.findByMunicipalityId(id);
            if(municipalityForecast == null){
                municipalityForecast = new MunicipalityForecast();
            }
            municipality.setMunicipalityForecast(municipalityForecast);
            municipalityForecast.setMunicipality(municipality);
            
            municipalityService.save(municipality);
            return "redirect:/admin/municipalities/" + id;
        }
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteMunicipality(@RequestBody List<Municipality> listMunicipalities){
//        listMunicipalities.forEach( m -> {
//                MunicipalityForecast mf = m.getMunicipalityForecast();
//                
////                m.setMunicipalityForecast(null);
//                
////                municipalityService.delete(m);
//                municipalityForecastService.delete(mf);
//            }
//        
//        );
        municipalityService.deleteAll(listMunicipalities);
    }
}
