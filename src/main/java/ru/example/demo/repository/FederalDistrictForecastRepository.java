package ru.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.FederalDistrictForecast;

public interface FederalDistrictForecastRepository extends JpaRepository<FederalDistrictForecast, Long>{
    List<FederalDistrictForecast> findAllByFederalDistrictId(Long id);
}
