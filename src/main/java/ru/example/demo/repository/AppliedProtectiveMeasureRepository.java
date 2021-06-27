package ru.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ru.example.demo.domain.AppliedProtectiveMeasure;

public interface AppliedProtectiveMeasureRepository extends CrudRepository<AppliedProtectiveMeasure, Long>{   
    List<AppliedProtectiveMeasure> findAllByMunicipalityForecastId(Long municipalityForecastId);
}
