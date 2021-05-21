package ru.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.FederalSubject;

public interface FederalSubjectRepository extends JpaRepository<FederalSubject, Long>{
}
