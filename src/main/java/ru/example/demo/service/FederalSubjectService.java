package ru.example.demo.service;

import ru.example.demo.domain.FederalSubject;

import java.util.List;

public interface FederalSubjectService extends JpaService<FederalSubject, Long> {
    List<FederalSubject> findAllByFederalDistrictId(Long id);
}