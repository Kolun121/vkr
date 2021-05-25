package ru.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.CrowdedPlace;


public interface CrowdedPlaceRepository extends JpaRepository<CrowdedPlace, Long>{
    List<CrowdedPlace> findAllByWaterBodyId(Long id);
}
