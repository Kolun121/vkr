package ru.example.demo.repository;

import java.util.List;
import ru.example.demo.domain.WaterBody;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterBodyRepository extends JpaRepository<WaterBody, Long>{
    List<WaterBody> findAllByMunicipalityId(Long id);
}
