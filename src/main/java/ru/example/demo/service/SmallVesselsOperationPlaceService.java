package ru.example.demo.service;

import ru.example.demo.domain.SmallVesselsOperationPlace;

import java.util.List;


public interface SmallVesselsOperationPlaceService extends CrudService<SmallVesselsOperationPlace, Long> {
    List<SmallVesselsOperationPlace> findAllByWaterBodyId(Long id);
}