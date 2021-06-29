package ru.example.demo.controller.admin;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.example.demo.domain.FederalSubjectForecast;
import ru.example.demo.helper.MapBindingHelper;
import ru.example.demo.helper.objects.MapData;
import ru.example.demo.service.FederalSubjectForecastService;

@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController {
    private final FederalSubjectForecastService federalSubjectForecastService;
    
    public IndexController(FederalSubjectForecastService federalSubjectForecastService) {
        this.federalSubjectForecastService = federalSubjectForecastService;
    }
    
    
    @GetMapping
    public String getIndexPage(Model model) {
        return "admin/index";
    }
    
    @GetMapping("/get-map-data")
    @ResponseBody
    public List<MapData> getMapData(){
        List<FederalSubjectForecast> forecasts 
                = federalSubjectForecastService
                    .findAll()
                    .stream()
                    .filter(forecast -> forecast.getFederalSubject().getMapIdValue() != null && forecast.getPopulationDynamicsCoefficient() != null)
                    .collect(Collectors.toList());
        
        return MapBindingHelper.getMapData(forecasts);
    }

}
