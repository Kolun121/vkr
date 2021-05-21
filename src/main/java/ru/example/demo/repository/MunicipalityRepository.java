package ru.example.demo.repository;

import ru.example.demo.domain.Municipality;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long>{
}
