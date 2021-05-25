package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.PlannedProtectiveMeasure;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.PlannedProtectiveMeasureRepository;
import ru.example.demo.service.PlannedProtectiveMeasureService;

@Service
public class PlannedProtectiveMeasureServiceImpl extends CrudServiceImpl<PlannedProtectiveMeasure, Long, PlannedProtectiveMeasureRepository> implements PlannedProtectiveMeasureService{

    public PlannedProtectiveMeasureServiceImpl(PlannedProtectiveMeasureRepository rRepository) {
        super(rRepository);
    }

    @Override
    public Iterable<PlannedProtectiveMeasure> saveAll(List<PlannedProtectiveMeasure> itrbl){
        return rRepository.saveAll(itrbl);
    }
}
