package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.FederalSubjectForecast;
import ru.example.demo.helper.comparator.FederalSubjectForecastComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.FederalSubjectForecastRepository;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.FederalSubjectForecastService;

@Service
public class FederalSubjectForecastServiceImpl extends AbstractJpaFilteringSortingServiceImpl<FederalSubjectForecast, Long, FederalSubjectForecastRepository> implements FederalSubjectForecastService{
    
    public FederalSubjectForecastServiceImpl(FederalSubjectForecastRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<FederalSubjectForecast> formTPredicate(String value) {
        Predicate<FederalSubjectForecast> predicate = m -> 
        {
            return m.getId().toString().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<FederalSubjectForecast> getTComparator(String name, Direction dir) {
        return FederalSubjectForecastComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<FederalSubjectForecast> getTEmptyComparator() {
        return FederalSubjectForecastComparators.getEmptyComparator();
    }
    
    @Override
    public List<FederalSubjectForecast> findAllByFederalSubjectId(Long id) {
        return this.rRepository.findAllByFederalSubjectId(id);
    }
    
    @Override
    public Page<FederalSubjectForecast> findAllByFederalSubjectIdPagingRequest(Long id, PagingRequest pagingRequest) {
        List<FederalSubjectForecast> ts = rRepository.findAllByFederalSubjectId(id);
       
        return getPage(ts, pagingRequest);
    }
}
