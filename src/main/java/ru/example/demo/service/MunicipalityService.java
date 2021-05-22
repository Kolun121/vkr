package ru.example.demo.service;

import ru.example.demo.domain.Municipality;

import java.util.List;


public interface MunicipalityService extends JpaService<Municipality, Long> {
    List<Municipality> findAllByFederalSubjectId(Long id);
}