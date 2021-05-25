package ru.example.demo.service;

import java.util.List;
import ru.example.demo.domain.PlannedProtectiveMeasure;


public interface PlannedProtectiveMeasureService extends CrudService<PlannedProtectiveMeasure, Long> {
    Iterable<PlannedProtectiveMeasure> saveAll(List<PlannedProtectiveMeasure> itrbl);
}