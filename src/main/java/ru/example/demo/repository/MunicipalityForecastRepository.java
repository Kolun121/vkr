package ru.example.demo.repository;

import java.util.List;
import ru.example.demo.domain.MunicipalityForecast;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityForecastRepository extends JpaRepository<MunicipalityForecast, Long>{
    List<MunicipalityForecast> findAllByMunicipalityId(Long id);
}
