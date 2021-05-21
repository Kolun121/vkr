package ru.example.demo.service;

import ru.example.demo.domain.Municipality;

import java.util.List;


public interface MunicipalityService extends CrudService<Municipality, Long> {
    List<Municipality> findAllByFederalSubjectId(Long id);
}