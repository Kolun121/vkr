package ru.example.demo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.example.demo.domain.CrowdedPlace;


public interface CrowdedPlaceRepository extends CrudRepository<CrowdedPlace, Long>{
    List<CrowdedPlace> findAllByWaterBodyId(Long id);
}
