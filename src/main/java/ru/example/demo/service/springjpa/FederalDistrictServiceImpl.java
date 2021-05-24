package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

import ru.example.demo.domain.FederalDistrict;
import ru.example.demo.helper.comparator.FederalDistrictComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.FederalDistrictRepository;
import ru.example.demo.service.FederalDistrictService;

@Service
public class FederalDistrictServiceImpl extends AbstractJpaFilteringSortingServiceImpl<FederalDistrict, Long, FederalDistrictRepository> implements FederalDistrictService{
    
    public FederalDistrictServiceImpl(FederalDistrictRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<FederalDistrict> formTPredicate(String value) {
        Predicate<FederalDistrict> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<FederalDistrict> getTComparator(String name, Direction dir) {
        return FederalDistrictComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<FederalDistrict> getTEmptyComparator() {
        return FederalDistrictComparators.getEmptyComparator();
    }
    
    @Override
    public FederalDistrict save(FederalDistrict object) {
        return super.save(object);
    }
   
}
