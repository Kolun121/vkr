package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.CrowdedPlace;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.repository.CrowdedPlaceRepository;
import ru.example.demo.service.CrowdedPlaceService;

@Service
public class CrowdedPlaceServiceImpl extends AbstractJpaFilteringSortingServiceImpl<CrowdedPlace, Long, CrowdedPlaceRepository> implements CrowdedPlaceService{
    
    public CrowdedPlaceServiceImpl(CrowdedPlaceRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<CrowdedPlace> formTPredicate(String value) {
        Predicate<CrowdedPlace> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<CrowdedPlace> getTComparator(String name, Direction dir) {
        return new Comparator<CrowdedPlace>() {
            @Override
            public int compare(CrowdedPlace o1, CrowdedPlace o2) {
                return 0;
            }
        };
    }

    @Override
    protected Comparator<CrowdedPlace> getTEmptyComparator() {
        return new Comparator<CrowdedPlace>() {
            @Override
            public int compare(CrowdedPlace o1, CrowdedPlace o2) {
                return 0;
            }
        };
    }

    @Override
    public List<CrowdedPlace> findAllByWaterBodyId(Long id) {
        return this.rRepository.findAllByWaterBodyId(id);
    }
    
    @Override
    public Page<CrowdedPlace> findAllByWaterBodyIdPagingRequest(Long id, PagingRequest pagingRequest) {
        List<CrowdedPlace> ts = rRepository.findAllByWaterBodyId(id);
       
        return getPage(ts, pagingRequest);
    }
    
}
