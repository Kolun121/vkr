package ru.example.demo.repository;

import ru.example.demo.domain.MunicipalityForecast;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityForecastRepository extends JpaRepository<MunicipalityForecast, Long>{
    MunicipalityForecast findByMunicipalityId(Long municipalityId);
}
