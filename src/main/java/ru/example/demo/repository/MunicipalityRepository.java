package ru.example.demo.repository;

import java.util.List;
import ru.example.demo.domain.Municipality;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long>{
    List<Municipality> findAllByFederalSubjectId(Long id);
}
