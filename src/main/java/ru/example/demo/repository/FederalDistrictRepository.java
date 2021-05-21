package ru.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.FederalDistrict;

public interface FederalDistrictRepository extends JpaRepository<FederalDistrict, Long>{
}
