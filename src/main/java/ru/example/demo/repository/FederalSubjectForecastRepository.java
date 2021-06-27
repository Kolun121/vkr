package ru.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.FederalSubjectForecast;

public interface FederalSubjectForecastRepository extends JpaRepository<FederalSubjectForecast, Long>{
    FederalSubjectForecast findByFederalSubjectId(Long federalSubjectId);
}
