package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

import ru.example.demo.domain.MunicipalityType;
import ru.example.demo.helper.comparator.MunicipalityTypeComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.MunicipalityTypeRepository;
import ru.example.demo.service.MunicipalityTypeService;

@Service
public class MunicipalityTypeServiceImpl extends AbstractJpaFilteringSortingServiceImpl<MunicipalityType, Long, MunicipalityTypeRepository> implements MunicipalityTypeService{
    
    public MunicipalityTypeServiceImpl(MunicipalityTypeRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<MunicipalityType> formTPredicate(String value) {
        Predicate<MunicipalityType> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<MunicipalityType> getTComparator(String name, Direction dir) {
        return MunicipalityTypeComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<MunicipalityType> getTEmptyComparator() {
        return MunicipalityTypeComparators.getEmptyComparator();
    }

}
