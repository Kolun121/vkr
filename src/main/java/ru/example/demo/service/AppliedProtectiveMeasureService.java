package ru.example.demo.service;

import java.util.List;
import ru.example.demo.domain.AppliedProtectiveMeasure;


public interface AppliedProtectiveMeasureService extends CrudService<AppliedProtectiveMeasure, Long> {
    Iterable<AppliedProtectiveMeasure> saveAll(List<AppliedProtectiveMeasure> itrbl);
    List<AppliedProtectiveMeasure> findAllByMunicipalityForecastId(Long municipalityForecastId);
}