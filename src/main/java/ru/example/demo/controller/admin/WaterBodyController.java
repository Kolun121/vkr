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
import ru.example.demo.domain.Municipality;

import ru.example.demo.domain.WaterBody;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.objects.Breadcrumb;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityService;
import ru.example.demo.service.WaterBodyService;


@Controller("adminWaterBodyController")
@RequestMapping("/admin/municipalities")
public class WaterBodyController {
    private static final String ADMIN_WATER_BODY_PATH = "admin/water_body";
    
    private final WaterBodyService waterBodyService;
    private final MunicipalityService municipalityService;
    
    
    public WaterBodyController(
            WaterBodyService waterBodyService,
            MunicipalityService municipalityService
    ) {
        this.waterBodyService = waterBodyService;
        this.municipalityService = municipalityService;
    }

    @GetMapping("{municipalityId}/water-bodies")
    public String listMunicipalityWaterBodies(@PathVariable Long municipalityId, Model model){
        long [] ids = new long[]{municipalityId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.MUNICIPALITY_WATER_BODIES, ids));
        
        model.addAttribute("municipalityId", municipalityId);
        return ADMIN_WATER_BODY_PATH + "/listMunicipalityWaterBodies";
    }
    
    @PostMapping("{municipalityId}/water-bodies")
    @ResponseBody
    public Page<WaterBody> listMunicipalityWaterBodiesAjax(@PathVariable Long municipalityId, @RequestBody PagingRequest pagingRequest) {

        return waterBodyService.findAllByMunicipalityIdPagingRequest(municipalityId, pagingRequest);
    }
    
    @PostMapping("{municipalityId}/water-bodies/new")
    public @ResponseBody String newMunicipalityWaterBody(@PathVariable Long municipalityId){
        WaterBody newWaterBody = new WaterBody();
        
        Municipality municipality = municipalityService.findById(municipalityId);
        
        newWaterBody.setMunicipality(municipality);
        
        WaterBody savedWaterBody = waterBodyService.save(newWaterBody);

        return savedWaterBody.getId().toString();
    }
    
    @GetMapping("{municipalityId}/water-bodies/{waterBodyId}")
    public String getWaterBodyById(@PathVariable Long municipalityId, @PathVariable Long waterBodyId, Model model) {
        long [] ids = new long[]{municipalityId, waterBodyId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.WATER_BODY, ids));
        
        WaterBody waterBody = waterBodyService.findById(waterBodyId);
               
        model.addAttribute("waterBody", waterBody);
        
        return ADMIN_WATER_BODY_PATH + "/updateWaterBody";
    }
    
    @PostMapping("{municipalityId}/water-bodies/{waterBodyId}")
    public String updateWaterBodyById(Model model, @PathVariable Long municipalityId, @PathVariable Long waterBodyId, @Valid WaterBody waterBody, BindingResult result) {
        if (result.hasErrors()) {
            long [] ids = new long[]{municipalityId, waterBodyId};
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.WATER_BODY, ids));
        
            return ADMIN_WATER_BODY_PATH + "/updateWaterBody";
        } else {
            waterBody.setId(waterBodyId);
            waterBody.setMunicipality(municipalityService.findById(municipalityId));
             
            waterBodyService.save(waterBody);
            return "redirect:/admin/municipalities/" + municipalityId + "/water-bodies/" + waterBodyId;
        }
    }
    
    @DeleteMapping("{municipalityId}/water-bodies/delete")
    @ResponseBody
    public void deleteMunicipalityWaterBodies(@RequestBody List<WaterBody> listWaterBodies){
        waterBodyService.deleteAll(listWaterBodies);
    }
    
}
