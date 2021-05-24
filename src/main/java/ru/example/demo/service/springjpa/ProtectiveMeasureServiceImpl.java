package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.ProtectiveMeasure;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.ProtectiveMeasureRepository;
import ru.example.demo.service.ProtectiveMeasureService;

@Service
public class ProtectiveMeasureServiceImpl extends AbstractJpaFilteringSortingServiceImpl<ProtectiveMeasure, Long, ProtectiveMeasureRepository> implements ProtectiveMeasureService{
    
    public ProtectiveMeasureServiceImpl(ProtectiveMeasureRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<ProtectiveMeasure> formTPredicate(String value) {
        Predicate<ProtectiveMeasure> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<ProtectiveMeasure> getTComparator(String name, Direction dir) {
//        return MunicipalityComparators.getComparator(name, dir);
        return new Comparator<ProtectiveMeasure>() {
            @Override
            public int compare(ProtectiveMeasure o1, ProtectiveMeasure o2) {
                return 0;
            }
        };
    }

    @Override
    protected Comparator<ProtectiveMeasure> getTEmptyComparator() {
        return new Comparator<ProtectiveMeasure>() {
            @Override
            public int compare(ProtectiveMeasure o1, ProtectiveMeasure o2) {
                return 0;
            }
        };
    }

    
}
