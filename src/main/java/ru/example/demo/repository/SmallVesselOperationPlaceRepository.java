package ru.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.SmallVesselOperationPlace;


public interface SmallVesselOperationPlaceRepository extends JpaRepository<SmallVesselOperationPlace, Long>{
    List<SmallVesselOperationPlace> findAllByWaterBodyId(Long id);
}
