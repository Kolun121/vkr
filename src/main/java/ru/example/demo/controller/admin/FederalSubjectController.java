package ru.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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


@Controller("adminFederalSubjectController")
@RequestMapping("/admin/federal-subjects")
public class FederalSubjectController {
    private static final String ADMIN_FEDERAL_SUBJECT_PATH = "admin/federal_subject";
   
    private final FederalSubjectService federalSubjectService;
    private final FederalSubjectForecastService federalSubjectForecastService;
    private final MunicipalityService municipalityService;
    
    public FederalSubjectController(
            FederalSubjectService federalDistrictService,
            FederalSubjectForecastService federalSubjectForecastService,
            MunicipalityService municipalityService
            ) 
    {
        this.federalSubjectService = federalDistrictService;
        this.federalSubjectForecastService = federalSubjectForecastService;
        this.municipalityService = municipalityService;
    }
    
    
    @GetMapping
    public String getFederalSubjectsPage(Model model){
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.FEDERAL_SUBJECTS));
        return ADMIN_FEDERAL_SUBJECT_PATH + "/listFederalSubjects";
    }
    
    @PostMapping
    @ResponseBody
    public Page<FederalSubject> listFederalSubjectsAjax(@RequestBody PagingRequest pagingRequest) {
        Page<FederalSubject> temp = federalSubjectService.findAllPagingRequest(pagingRequest);
        return temp;
    }
    
    @GetMapping("/new")
    public String federalSubjectCreatePage(Model model){
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.FEDERAL_SUBJECT_CREATE));
        
        List<Municipality> availableMunicipalities = municipalityService.findAll().stream()
                .filter((Municipality municipality) -> {
                    if(municipality.getFederalSubject()!= null){
                        return false;
                    }
                    return true;
                })
                .collect((Collectors.toList()));
            
        model.addAttribute("federalSubjectTypeValues", FederalSubjectType.values());
        model.addAttribute("availableMunicipalities", availableMunicipalities);
        model.addAttribute("federalSubject", new FederalSubject());
        
        return ADMIN_FEDERAL_SUBJECT_PATH + "/createFederalSubject";
    }
    
    @PostMapping("/new") 
    public String createFederalSubject(Model model, @Valid FederalSubject federalSubject, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.FEDERAL_SUBJECT_CREATE));

            List<Municipality> availableMunicipalities = municipalityService.findAll().stream()
                .filter((Municipality municipality) -> {
                    if(municipality.getFederalSubject()!= null){
                        return false;
                    }
                    return true;
                })
                .collect((Collectors.toList()));

            model.addAttribute("federalSubjectTypeValues", FederalSubjectType.values());
            model.addAttribute("availableMunicipalities", availableMunicipalities);
            model.addAttribute("federalSubject", federalSubject);

            return ADMIN_FEDERAL_SUBJECT_PATH + "/createFederalSubject";
        } else {            
            FederalSubjectForecast federalSubjectForecast = new FederalSubjectForecast();

            federalSubject.setFederalSubjectForecast(federalSubjectForecast);
            federalSubjectForecast.setFederalSubject(federalSubject);

            for (Municipality chosenMunicipality : federalSubject.getMunicipalities()) {
                //Проводим расчеты для текущей численности населения субъекта
                int tempPopulation = federalSubject.getCurrentPopulation();
                int municipalityPopulation = chosenMunicipality.getCurrentPopulation();
                federalSubject.setCurrentPopulation(tempPopulation + municipalityPopulation);
                
                chosenMunicipality.setFederalSubject(federalSubject);
            }
            
            //Заполнение информации о населении в 1, 2, 3, 4, 5 год анализируемого периода
            List<MunicipalityForecast> municipalityForecasts = new ArrayList<>();
            federalSubject.getMunicipalities().forEach(m -> municipalityForecasts.add(m.getMunicipalityForecast()));
            calculateForecastPopulation(federalSubjectForecast, municipalityForecasts);
            
            FederalSubject savedFederalSubject = federalSubjectService.save(federalSubject);

            return "redirect:/admin/federal-subjects/" + savedFederalSubject.getId().toString();
        }
    }
    
    @GetMapping("{id}") 
    public String getFederalSubjectById(@PathVariable Long id, Model model) {
        FederalSubject federalSubject = federalSubjectService.findById(id);
        
        long [] ids = new long[]{id};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.FEDERAL_SUBJECT, ids));
        
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
    public String updateFederalSubjectById(Model model, @PathVariable Long id, @Valid FederalSubject federalSubject, BindingResult result) {
        if (result.hasErrors()) {
            
            long [] ids = new long[]{id};
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.FEDERAL_SUBJECT, ids));

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
        } else {
            federalSubject.setId(id);
            List<Municipality> municipalitiesOfSubject = municipalityService.findAllByFederalSubjectId(id);
            
            FederalSubjectForecast federalSubjectForecast = federalSubjectForecastService.findByFederalSubjectId(id);
            if(federalSubjectForecast == null){
                federalSubjectForecast = new FederalSubjectForecast();
            }
            federalSubject.setFederalSubjectForecast(federalSubjectForecast);
            federalSubjectForecast.setFederalSubject(federalSubject);
            
            //Выставляем текущую численность населения субъекта, равной нулю для последующих расчетов
            federalSubject.setCurrentPopulation(0);
            
            if(!municipalitiesOfSubject.isEmpty()){
                for (Municipality municipality : municipalitiesOfSubject) {
                    municipality.setFederalSubject(null);
                }
            }
            
            //dualbox list проверка и обновление муниципалитетов
            // Проходим по муниципалитетам, которые были в выбраны в форме
            // Выставляем субъект для выбранного муниципалитета
            for (Municipality chosenMunicipality : federalSubject.getMunicipalities()) {
                
//                if(municipalitiesOfSubject.contains(chosenMunicipality)){
//                    municipalitiesOfSubject.remove(chosenMunicipality);
//                }
                
                //Проводим расчеты для текущей численности населения субъекта
                int tempPopulation = federalSubject.getCurrentPopulation();
                int municipalityPopulation = chosenMunicipality.getCurrentPopulation();
                federalSubject.setCurrentPopulation(tempPopulation + municipalityPopulation);
                
                chosenMunicipality.setFederalSubject(federalSubject);
            }
            
            //Заполнение информации о населении в 1, 2, 3, 4, 5 год анализируемого периода
            List<MunicipalityForecast> municipalityForecasts = new ArrayList<>();
            federalSubject.getMunicipalities().forEach(m -> municipalityForecasts.add(m.getMunicipalityForecast()));
            calculateForecastPopulation(federalSubjectForecast, municipalityForecasts);
             
            municipalityService.saveAll(municipalitiesOfSubject);
            federalSubjectService.save(federalSubject);
            return "redirect:/admin/federal-subjects/" + id;
        }
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteFederalSubject(@RequestBody List<FederalSubject> listFederalDistricts){
        federalSubjectService.deleteAll(listFederalDistricts);
    }
    
    private void calculateForecastPopulation(FederalSubjectForecast forecast, List<MunicipalityForecast> mForecasts){
        forecast.setPopulationInFirstYear(0);
        forecast.setPopulationInSecondYear(0);
        forecast.setPopulationInThirdYear(0);
        forecast.setPopulationInFourthYear(0);
        forecast.setPopulationInFifthYear(0);
        
        for (MunicipalityForecast mForecast : mForecasts) {
            int population1 = (mForecast.getPopulationInFirstYear() != null) ? mForecast.getPopulationInFirstYear() : 0;
            int population2 = (mForecast.getPopulationInSecondYear() != null) ? mForecast.getPopulationInSecondYear() : 0;
            int population3 = (mForecast.getPopulationInThirdYear() != null) ? mForecast.getPopulationInThirdYear() : 0;
            int population4 = (mForecast.getPopulationInFourthYear() != null) ? mForecast.getPopulationInFourthYear() : 0;
            int population5 = (mForecast.getPopulationInFifthYear() != null) ? mForecast.getPopulationInFifthYear() : 0;
            
            int forecastPopulation1 = forecast.getPopulationInFirstYear();
            int forecastPopulation2 = forecast.getPopulationInSecondYear();
            int forecastPopulation3 = forecast.getPopulationInThirdYear();
            int forecastPopulation4 = forecast.getPopulationInFourthYear();
            int forecastPopulation5 = forecast.getPopulationInFifthYear();
            
            forecast.setPopulationInFirstYear(population1 + forecastPopulation1);
            forecast.setPopulationInSecondYear(population2 + forecastPopulation2);
            forecast.setPopulationInThirdYear(population3 + forecastPopulation3);
            forecast.setPopulationInFourthYear(population4 + forecastPopulation4);
            forecast.setPopulationInFifthYear(population4 + forecastPopulation4);
            forecast.setPopulationInFifthYear(population5 + forecastPopulation5);
        }
    }
}
