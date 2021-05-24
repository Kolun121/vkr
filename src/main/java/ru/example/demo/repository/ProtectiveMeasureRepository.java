package ru.example.demo.repository;

import ru.example.demo.domain.ProtectiveMeasure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtectiveMeasureRepository extends JpaRepository<ProtectiveMeasure, Long>{
}
