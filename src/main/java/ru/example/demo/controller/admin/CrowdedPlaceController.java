package ru.example.demo.controller.admin;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.example.demo.domain.CrowdedPlace;
import ru.example.demo.domain.ProtectiveMeasure;
import ru.example.demo.domain.WaterBody;
import ru.example.demo.domain.enumeration.PlaceType;
import ru.example.demo.domain.enumeration.TypeOfCrowdedPlace;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.CrowdedPlaceService;
import ru.example.demo.service.WaterBodyService;
import ru.example.demo.service.ProtectiveMeasureService;


@Controller("adminCrowdedPlaceController")
@RequestMapping("/admin/municipalities")
public class CrowdedPlaceController {
    private static final String ADMIN_CROWDED_PLACE_PATH = "admin/crowded_place";
    
    private final WaterBodyService waterBodyService;
    private final CrowdedPlaceService crowdedPlaceService;
    private final ProtectiveMeasureService protectiveMeasureService;
    
    
    public CrowdedPlaceController(
            WaterBodyService waterBodyService,
            CrowdedPlaceService crowdedPlaceService,
            ProtectiveMeasureService protectiveMeasureService
    ) {
        this.waterBodyService = waterBodyService;
        this.crowdedPlaceService = crowdedPlaceService;
        this.protectiveMeasureService = protectiveMeasureService;
    }
    
    @GetMapping("{municipalityId}/water-bodies/{waterBodyId}/crowded-places")
    public String listWaterBodyCrowdedPlaces(@PathVariable Long municipalityId, @PathVariable Long waterBodyId, Model model){
        long [] ids = new long[]{municipalityId, waterBodyId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.WATER_BODY_CROWDED_PLACES, ids));
        
        model.addAttribute("municipalityId", municipalityId);
        model.addAttribute("waterBodyId", waterBodyId);
        return ADMIN_CROWDED_PLACE_PATH + "/listWaterBodyCrowdedPlaces";
    }
    
    @PostMapping("{municipalityId}/water-bodies/{waterBodyId}/crowded-places")
    @ResponseBody
    public Page<CrowdedPlace> listWaterBodyCrowdedPlacesAjax(@PathVariable Long waterBodyId, @RequestBody PagingRequest pagingRequest) {

        return crowdedPlaceService.findAllByWaterBodyIdPagingRequest(waterBodyId, pagingRequest);
    }
    
    @PostMapping("{municipalityId}/water-bodies/{waterBodyId}/crowded-places/new")
    public @ResponseBody String newWaterBodyCrowdedPlace(@PathVariable Long waterBodyId){
        CrowdedPlace newCrowdedPlace = new CrowdedPlace();
        
        WaterBody waterBody = waterBodyService.findById(waterBodyId);
        
        newCrowdedPlace.setWaterBody(waterBody);
        
        CrowdedPlace savedCrowdedPlace = crowdedPlaceService.save(newCrowdedPlace);

        return savedCrowdedPlace.getId().toString();
    }
    
    @GetMapping("{municipalityId}/water-bodies/{waterBodyId}/crowded-places/{crowdedPlaceId}")
    public String getCrowdedPlaceById(@PathVariable Long municipalityId, @PathVariable Long waterBodyId, @PathVariable Long crowdedPlaceId, Model model) {
        long [] ids = new long[]{municipalityId, waterBodyId, crowdedPlaceId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.CROWDED_PLACE, ids));
        
        CrowdedPlace crowdedPlace = crowdedPlaceService.findById(crowdedPlaceId);
        
        List<ProtectiveMeasure> crowdedPlaceProtectiveMeasures = protectiveMeasureService.findAll()
                .stream()
                .filter((ProtectiveMeasure protectiveMeasure) -> {
                    if(protectiveMeasure.getDesignatedFor()!= null){
                        if(!protectiveMeasure.getDesignatedFor().equals(PlaceType.VESSEL_OPERATION_PLACE)){
                            return true;
                        }
                    }
                    return false;
                })
                .collect((Collectors.toList()));
                
        
        model.addAttribute("typeOfCrowdedPlaceValues", TypeOfCrowdedPlace.values());
        model.addAttribute("crowdedPlace", crowdedPlace);
        model.addAttribute("crowdedPlaceProtectiveMeasures", crowdedPlaceProtectiveMeasures);
        
        return ADMIN_CROWDED_PLACE_PATH + "/updateCrowdedPlace";
    }
    
    @PostMapping("{municipalityId}/water-bodies/{waterBodyId}/crowded-places/{crowdedPlaceId}")
    public String updateCrowdedPlaceById(Model model, @PathVariable Long municipalityId, @PathVariable Long waterBodyId, @PathVariable Long crowdedPlaceId, @Valid CrowdedPlace crowdedPlace, BindingResult result) {
        if (result.hasErrors()) {
            long [] ids = new long[]{municipalityId, waterBodyId, crowdedPlaceId};
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.CROWDED_PLACE, ids));
             List<ProtectiveMeasure> crowdedPlaceProtectiveMeasures = protectiveMeasureService.findAll()
                .stream()
                .filter((ProtectiveMeasure protectiveMeasure) -> {
                    if(protectiveMeasure.getDesignatedFor()!= null){
                        if(!protectiveMeasure.getDesignatedFor().equals(PlaceType.VESSEL_OPERATION_PLACE)){
                            return true;
                        }
                    }
                    return false;
                })
                .collect((Collectors.toList()));
                
        
            model.addAttribute("typeOfCrowdedPlaceValues", TypeOfCrowdedPlace.values());
            model.addAttribute("crowdedPlace", crowdedPlace);
            model.addAttribute("crowdedPlaceProtectiveMeasures", crowdedPlaceProtectiveMeasures);
            return ADMIN_CROWDED_PLACE_PATH + "/updateCrowdedPlace";
        } else {
            crowdedPlace.setId(crowdedPlaceId);
            if(crowdedPlace.getProtectiveMeasure() == null){
                crowdedPlace.setProtectiveMeasureCost(0);
            }
            crowdedPlaceService.save(crowdedPlace);
            return "redirect:/admin/municipalities/" + municipalityId + "/water-bodies/" + waterBodyId + "/crowded-places/" + crowdedPlaceId;
        }
    }
    
    @DeleteMapping("{municipalityId}/water-bodies/{waterBodyId}/crowded-places/delete")
    @ResponseBody
    public void deleteWaterBodyCrowdedPlaces(@RequestBody List<CrowdedPlace> listCrowdedPlaces){
        crowdedPlaceService.deleteAll(listCrowdedPlaces);
    }
    
}
