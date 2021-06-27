package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.AppliedProtectiveMeasure;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.AppliedProtectiveMeasureRepository;
import ru.example.demo.service.AppliedProtectiveMeasureService;

@Service
public class AppliedProtectiveMeasureServiceImpl extends CrudServiceImpl<AppliedProtectiveMeasure, Long, AppliedProtectiveMeasureRepository> implements AppliedProtectiveMeasureService{

    public AppliedProtectiveMeasureServiceImpl(AppliedProtectiveMeasureRepository rRepository) {
        super(rRepository);
    }

    @Override
    public Iterable<AppliedProtectiveMeasure> saveAll(List<AppliedProtectiveMeasure> itrbl){
        return rRepository.saveAll(itrbl);
    }

    @Override
    public List<AppliedProtectiveMeasure> findAllByMunicipalityForecastId(Long municipalityForecastId) {
        return rRepository.findAllByMunicipalityForecastId(municipalityForecastId);
    }
}
