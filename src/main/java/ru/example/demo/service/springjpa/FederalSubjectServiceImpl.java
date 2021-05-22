package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

import ru.example.demo.domain.FederalSubject;
import ru.example.demo.helper.comparator.FederalSubjectComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.FederalSubjectRepository;
import ru.example.demo.service.FederalSubjectService;

@Service
public class FederalSubjectServiceImpl extends AbstractJpaFilteringSortingServiceImpl<FederalSubject, Long, FederalSubjectRepository> implements FederalSubjectService{
    
    public FederalSubjectServiceImpl(FederalSubjectRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<FederalSubject> formTPredicate(String value) {
        Predicate<FederalSubject> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<FederalSubject> getTComparator(String name, Direction dir) {
        return FederalSubjectComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<FederalSubject> getTEmptyComparator() {
        return FederalSubjectComparators.getEmptyComparator();
    }

    @Override
    public List<FederalSubject> findAllByFederalDistrictId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
