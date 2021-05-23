package ru.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.FederalSubject;

public interface FederalSubjectRepository extends JpaRepository<FederalSubject, Long>{
    List<FederalSubject> findAllByFederalDistrictId(Long id);
}
