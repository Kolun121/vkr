package ru.example.demo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.example.demo.domain.SmallVesselsOperationPlace;


public interface SmallVesselsOperationPlaceRepository extends CrudRepository<SmallVesselsOperationPlace, Long>{
    List<SmallVesselsOperationPlace> findAllByWaterBodyId(Long id);
}
