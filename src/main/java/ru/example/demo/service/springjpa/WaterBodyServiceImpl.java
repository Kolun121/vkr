package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.WaterBody;
import ru.example.demo.helper.comparator.WaterBodyComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.repository.WaterBodyRepository;
import ru.example.demo.service.WaterBodyService;

@Service
public class WaterBodyServiceImpl extends AbstractJpaFilteringSortingServiceImpl<WaterBody, Long, WaterBodyRepository> implements WaterBodyService{
    
    public WaterBodyServiceImpl(WaterBodyRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<WaterBody> formTPredicate(String value) {
        Predicate<WaterBody> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<WaterBody> getTComparator(String name, Direction dir) {
        return WaterBodyComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<WaterBody> getTEmptyComparator() {
        return WaterBodyComparators.getEmptyComparator();
    }

    @Override
    public List<WaterBody> findAllByMunicipalityId(Long id) {
        return this.rRepository.findAllByMunicipalityId(id);
    }
    
    @Override
    public Page<WaterBody> findAllByMunicipalityIdPagingRequest(Long id, PagingRequest pagingRequest) {
        List<WaterBody> ts = rRepository.findAllByMunicipalityId(id);
       
        return getPage(ts, pagingRequest);
    }
    
}
