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
import ru.example.demo.domain.ProtectiveMeasure;

import ru.example.demo.domain.SmallVesselOperationPlace;
import ru.example.demo.domain.WaterBody;
import ru.example.demo.domain.enumeration.PlaceType;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.ProtectiveMeasureService;
import ru.example.demo.service.SmallVesselOperationPlaceService;
import ru.example.demo.service.WaterBodyService;


@Controller("adminSmallVesselOperationPlaceController")
@RequestMapping("/admin/municipalities")
public class SmallVesselOperationPlaceController {
    private static final String ADMIN_VESSEL_PLACE_PATH = "admin/small_vessel_operation_place";
    
    private final WaterBodyService waterBodyService;
    private final SmallVesselOperationPlaceService smallVesselOperationPlaceService;
    private final ProtectiveMeasureService protectiveMeasureService;
    
    
    public SmallVesselOperationPlaceController(
            WaterBodyService waterBodyService,
            SmallVesselOperationPlaceService smallVesselOperationPlaceService,
            ProtectiveMeasureService protectiveMeasureService
    ) {
        this.waterBodyService = waterBodyService;
        this.smallVesselOperationPlaceService = smallVesselOperationPlaceService;
        this.protectiveMeasureService = protectiveMeasureService;
    }

    @GetMapping("{municipalityId}/water-bodies/{waterBodyId}/vessel-operation-places")
    public String listWaterBodySmallVesselOperationPlaces(@PathVariable Long municipalityId, @PathVariable Long waterBodyId, Model model){
        
        long [] ids = new long[]{municipalityId, waterBodyId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.WATER_BODY_SMALL_VESSELS, ids));
        
        model.addAttribute("municipalityId", municipalityId);
        model.addAttribute("waterBodyId", waterBodyId);
        return ADMIN_VESSEL_PLACE_PATH + "/listWaterBodyVesselOperaionPlaces";
    }
    
    @PostMapping("{municipalityId}/water-bodies/{waterBodyId}/vessel-operation-places")
    @ResponseBody
    public Page<SmallVesselOperationPlace> listWaterBodySmallVesselOperationPlacesAjax(@PathVariable Long waterBodyId, @RequestBody PagingRequest pagingRequest) {

        return smallVesselOperationPlaceService.findAllByWaterBodyIdPagingRequest(waterBodyId, pagingRequest);
    }
    
    @PostMapping("{municipalityId}/water-bodies/{waterBodyId}/vessel-operation-places/new")
    public @ResponseBody String newWaterBodySmallVesselOperationPlace(@PathVariable Long waterBodyId){
        SmallVesselOperationPlace newSmallVesselOperationPlace = new SmallVesselOperationPlace();
        
        WaterBody waterBody = waterBodyService.findById(waterBodyId);
        
        newSmallVesselOperationPlace.setWaterBody(waterBody);
        
        SmallVesselOperationPlace savedSmallVesselOperationPlace = smallVesselOperationPlaceService.save(newSmallVesselOperationPlace);

        return savedSmallVesselOperationPlace.getId().toString();
    }
    
    @GetMapping("{municipalityId}/water-bodies/{waterBodyId}/vessel-operation-places/{vesselOperationPlaceId}")
    public String getVesselOperationPlaceById(@PathVariable Long municipalityId, @PathVariable Long waterBodyId, @PathVariable Long vesselOperationPlaceId, Model model) {
        SmallVesselOperationPlace smallVesselOperationPlace = smallVesselOperationPlaceService.findById(vesselOperationPlaceId);
               
        long [] ids = new long[]{municipalityId, waterBodyId, vesselOperationPlaceId};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.SMALL_VESSEL, ids));
        
        List<ProtectiveMeasure> vesselOperationPlaceProtectiveMeasures = protectiveMeasureService.findAll()
                .stream()
                .filter((ProtectiveMeasure protectiveMeasure) -> {
                    if(protectiveMeasure.getDesignatedFor()!= null){
                        if(!protectiveMeasure.getDesignatedFor().equals(PlaceType.VESSEL_OPERATION_PLACE)){
                            return false;
                        }
                    }
                    return true;
                })
                .collect((Collectors.toList()));
        
        model.addAttribute("smallVesselOperationPlace", smallVesselOperationPlace);
        model.addAttribute("vesselOperationPlaceProtectiveMeasures", vesselOperationPlaceProtectiveMeasures);
        
        return ADMIN_VESSEL_PLACE_PATH + "/updateVesselOperaionPlace";
    }
    
    @PostMapping("{municipalityId}/water-bodies/{waterBodyId}/vessel-operation-places/{vesselOperationPlaceId}")
    public String updateVesselOperationPlaceById(Model model, @PathVariable Long municipalityId, @PathVariable Long waterBodyId, @PathVariable Long vesselOperationPlaceId, @Valid SmallVesselOperationPlace smallVesselOperationPlace, BindingResult result) {
        if (result.hasErrors()) {
            
            long [] ids = new long[]{municipalityId, waterBodyId, vesselOperationPlaceId};
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.SMALL_VESSEL, ids));

            List<ProtectiveMeasure> vesselOperationPlaceProtectiveMeasures = protectiveMeasureService.findAll()
                    .stream()
                    .filter((ProtectiveMeasure protectiveMeasure) -> {
                        if(protectiveMeasure.getDesignatedFor()!= null){
                            if(!protectiveMeasure.getDesignatedFor().equals(PlaceType.VESSEL_OPERATION_PLACE)){
                                return false;
                            }
                        }
                        return true;
                    })
                    .collect((Collectors.toList()));

            model.addAttribute("smallVesselOperationPlace", smallVesselOperationPlace);
            model.addAttribute("vesselOperationPlaceProtectiveMeasures", vesselOperationPlaceProtectiveMeasures);
        
            return ADMIN_VESSEL_PLACE_PATH + "/updateVesselOperaionPlace";
        } else {
            smallVesselOperationPlace.setId(vesselOperationPlaceId);
            
            if(smallVesselOperationPlace.getProtectiveMeasure() == null){
                smallVesselOperationPlace.setProtectiveMeasureCost(0);
            }
            
            smallVesselOperationPlaceService.save(smallVesselOperationPlace);
            return "redirect:/admin/municipalities/" + municipalityId + "/water-bodies/" + waterBodyId + "/vessel-operation-places/" + vesselOperationPlaceId;
        }
    }
    
    @DeleteMapping("{municipalityId}/water-bodies/{waterBodyId}/vessel-operation-places/delete")
    @ResponseBody
    public void deleteWaterBodyVesselOperationPlaces(@RequestBody List<SmallVesselOperationPlace> listSmallVesselOperationPlaces){
        smallVesselOperationPlaceService.deleteAll(listSmallVesselOperationPlaces);
    }
    
}
