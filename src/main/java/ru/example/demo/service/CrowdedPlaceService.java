package ru.example.demo.service;

import ru.example.demo.domain.CrowdedPlace;

import java.util.List;


public interface CrowdedPlaceService extends CrudService<CrowdedPlace, Long> {
    List<CrowdedPlace> findAllByWaterBodyId(Long id);
}