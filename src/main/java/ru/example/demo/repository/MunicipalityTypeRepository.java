package ru.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.MunicipalityType;


public interface MunicipalityTypeRepository extends JpaRepository<MunicipalityType, Long>{
}
