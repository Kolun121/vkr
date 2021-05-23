package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.Municipality;
import ru.example.demo.helper.comparator.MunicipalityComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.MunicipalityRepository;
import ru.example.demo.service.MunicipalityService;

@Service
public class MunicipalityServiceImpl extends AbstractJpaFilteringSortingServiceImpl<Municipality, Long, MunicipalityRepository> implements MunicipalityService{
    
    public MunicipalityServiceImpl(MunicipalityRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<Municipality> formTPredicate(String value) {
        Predicate<Municipality> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<Municipality> getTComparator(String name, Direction dir) {
        return MunicipalityComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<Municipality> getTEmptyComparator() {
        return MunicipalityComparators.getEmptyComparator();
    }

    @Override
    public List<Municipality> findAllByFederalSubjectId(Long id) {
        return this.rRepository.findAllByFederalSubjectId(id);
    }
    
}
