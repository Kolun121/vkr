package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.MunicipalityForecast;
import ru.example.demo.helper.comparator.MunicipalityForecastComparators;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.repository.MunicipalityForecastRepository;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.MunicipalityForecastService;

@Service
public class MunicipalityForecastServiceImpl extends AbstractJpaFilteringSortingServiceImpl<MunicipalityForecast, Long, MunicipalityForecastRepository> implements MunicipalityForecastService{
    
    public MunicipalityForecastServiceImpl(MunicipalityForecastRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<MunicipalityForecast> formTPredicate(String value) {
        Predicate<MunicipalityForecast> predicate = m -> 
        {
            return m.getYear().toString().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<MunicipalityForecast> getTComparator(String name, Direction dir) {
        return MunicipalityForecastComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<MunicipalityForecast> getTEmptyComparator() {
        return MunicipalityForecastComparators.getEmptyComparator();
    }
    
    @Override
    public List<MunicipalityForecast> findAllByMunicipalityId(Long id) {
        return this.rRepository.findAllByMunicipalityId(id);
    }
    
    @Override
    public Page<MunicipalityForecast> findAllByMunicipalityIdPagingRequest(Long id, PagingRequest pagingRequest) {
        List<MunicipalityForecast> ts = rRepository.findAllByMunicipalityId(id);
       
        return getPage(ts, pagingRequest);
    }
    
}
