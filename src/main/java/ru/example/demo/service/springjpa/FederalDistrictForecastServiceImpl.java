package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.FederalDistrictForecast;
import ru.example.demo.helper.comparator.FederalDistrictForecastComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.FederalDistrictForecastRepository;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.FederalDistrictForecastService;

@Service
public class FederalDistrictForecastServiceImpl extends AbstractJpaFilteringSortingServiceImpl<FederalDistrictForecast, Long, FederalDistrictForecastRepository> implements FederalDistrictForecastService{
    
    public FederalDistrictForecastServiceImpl(FederalDistrictForecastRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<FederalDistrictForecast> formTPredicate(String value) {
        Predicate<FederalDistrictForecast> predicate = m -> 
        {
            return m.getYear().toString().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<FederalDistrictForecast> getTComparator(String name, Direction dir) {
        return FederalDistrictForecastComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<FederalDistrictForecast> getTEmptyComparator() {
        return FederalDistrictForecastComparators.getEmptyComparator();
    }
    
    @Override
    public List<FederalDistrictForecast> findAllByFederalDistrictId(Long id) {
        return this.rRepository.findAllByFederalDistrictId(id);
    }
    
    @Override
    public Page<FederalDistrictForecast> findAllByFederalDistrictIdPagingRequest(Long id, PagingRequest pagingRequest) {
        List<FederalDistrictForecast> ts = rRepository.findAllByFederalDistrictId(id);
       
        return getPage(ts, pagingRequest);
    }
    
}
