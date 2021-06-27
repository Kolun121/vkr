package ru.example.demo.helper;

import java.util.ArrayList;
import java.util.List;
import ru.example.demo.helper.objects.Breadcrumb;
import ru.example.demo.helper.objects.BreadcrumbsKind;

public class BreadcrumbsListFactory {
    private static final List<Breadcrumb> municipalitiesBreadcrumbs = new ArrayList<>();
    private static final List<Breadcrumb> municipalityNewBreadcrumbs = new ArrayList<>();
    private static final List<Breadcrumb> federalSubjectsBreadcrumbs = new ArrayList<>();
    private static final List<Breadcrumb> federalSubjectNewBreadcrumbs = new ArrayList<>();
    private static final List<Breadcrumb> protectiveMeasuresBreadcrumbs = new ArrayList<>();
    
    static {
        municipalitiesBreadcrumbs.add(new Breadcrumb("", "/admin", "fa-home"));
        municipalitiesBreadcrumbs.add(new Breadcrumb("Муниципалитеты", "/admin/municipalities", "fa-book"));
        
        federalSubjectsBreadcrumbs.add(new Breadcrumb("", "/admin", "fa-home"));
        federalSubjectsBreadcrumbs.add(new Breadcrumb("Субъекты", "/admin/federal-subjects", "fa-book"));
        
        municipalityNewBreadcrumbs.add(new Breadcrumb("", "/admin", "fa-home"));
        municipalityNewBreadcrumbs.add(new Breadcrumb("Муниципалитеты", "/admin/municipalities", "fa-book"));
        municipalityNewBreadcrumbs.add(new Breadcrumb("Новый муниципалитет", "/admin/municipalities/new", "fa-home"));
        
        federalSubjectNewBreadcrumbs.add(new Breadcrumb("", "/admin", "fa-home"));
        federalSubjectNewBreadcrumbs.add(new Breadcrumb("Субъекты", "/admin/federal-subjects", "fa-book"));
        federalSubjectNewBreadcrumbs.add(new Breadcrumb("Новый субъект", "/admin/federal-subjects/new", "fa-home"));
        
        protectiveMeasuresBreadcrumbs.add(new Breadcrumb("", "/admin", "fa-home"));
        protectiveMeasuresBreadcrumbs.add(new Breadcrumb("Защитные меры", "/admin/protective-measures", "fa-book"));
    }
    
    
    public static List<Breadcrumb> getBreadcrumbsList(BreadcrumbsKind kind){        
        switch(kind){
            case MUNICIPALITIES:
                return municipalitiesBreadcrumbs;
            case MUNICIPALITY_CREATE:
                return municipalityNewBreadcrumbs;
            case FEDERAL_SUBJECTS:
                return federalSubjectsBreadcrumbs;
            case FEDERAL_SUBJECT_CREATE:
                return federalSubjectNewBreadcrumbs;
//            case FEDERAL_SUBJECT_FORECAST:
//                break;
        }
    
        return null;
    }
    
    public static List<Breadcrumb> getBreadcrumbsListWithParams(BreadcrumbsKind kind, long[] params){
        
        List<Breadcrumb> breadcrumbs;
        
        switch(kind){
            case MUNICIPALITY:
                if(params.length == 1){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    return breadcrumbs;
                }
                break;
            case MUNICIPALITY_FORECAST:
                if(params.length == 1){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Расчет рисков", "/admin/municipalities/" + params[0] + "/forecast", "fa-calculator"));
                    return breadcrumbs;
                }
                break;
            case MUNICIPALITY_WATER_BODIES:
                if(params.length == 1){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Водные объекты", "/admin/municipalities/" + params[0] + "/water-bodies", "fa-book"));
                    return breadcrumbs;
                }
                break;
            case WATER_BODY:
                if(params.length == 2){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Водные объекты", "/admin/municipalities/" + params[0] + "/water-bodies", "fa-book"));
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1], "fa-edit"));
                    return breadcrumbs;
                }
                break;
            case WATER_BODY_CROWDED_PLACES:
                if(params.length == 2){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Водные объекты", "/admin/municipalities/" + params[0] + "/water-bodies", "fa-book"));
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Места МСЛ", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1] + "/crowded-places", "fa-book"));
                    return breadcrumbs;
                }
                break;
            case WATER_BODY_SMALL_VESSELS:
                if(params.length == 2){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Водные объекты", "/admin/municipalities/" + params[0] + "/water-bodies", "fa-book"));
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1], "fa-edit"));
                    
                    breadcrumbs.add(new Breadcrumb("Места ММС", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1] + "/vessel-operation-places", "fa-book"));
                    return breadcrumbs;
                }
                break;
            case CROWDED_PLACE:
                
                if(params.length == 3){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Водные объекты", "/admin/municipalities/" + params[0] + "/water-bodies", "fa-book"));
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1], "fa-edit"));
                    
                    breadcrumbs.add(new Breadcrumb("Места МСЛ", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1] + "/crowded-places", "fa-book"));
                    
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1] + "/crowded-places/" + params[2], "fa-edit"));
                    return breadcrumbs;
                }
                
                break;
            case SMALL_VESSEL:
                if(params.length == 3){
                    breadcrumbs = new ArrayList<>(municipalitiesBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Водные объекты", "/admin/municipalities/" + params[0] + "/water-bodies", "fa-book"));
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1], "fa-edit"));
                    
                    breadcrumbs.add(new Breadcrumb("Места МСЛ", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1] + "/vessel-operation-places", "fa-book"));
                    
                    breadcrumbs.add(new Breadcrumb("", "/admin/municipalities/" + params[0] + "/water-bodies/" + params[1] + "/vessel-operation-places/" + params[2], "fa-edit"));
                    return breadcrumbs;
                }
                break;
            case FEDERAL_SUBJECT:
                if(params.length == 1){
                    breadcrumbs = new ArrayList<>(federalSubjectsBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/federal-subjects/" + params[0], "fa-edit"));
                    return breadcrumbs;
                }
                break;
            case FEDERAL_SUBJECT_FORECAST:
                if(params.length == 1){
                    breadcrumbs = new ArrayList<>(federalSubjectsBreadcrumbs);
                    breadcrumbs.add(new Breadcrumb("", "/admin/federal-subjects/" + params[0], "fa-edit"));
                    breadcrumbs.add(new Breadcrumb("Расчет рисков", "/admin/federal-subjects/" + params[0] + "/forecast", "fa-calculator"));
                    return breadcrumbs;
                }
                break;
        }
        return new ArrayList<>();
    }
}
