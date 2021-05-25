package ru.example.demo.service.springjpa;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.SmallVesselOperationPlace;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.repository.SmallVesselOperationPlaceRepository;
import ru.example.demo.service.SmallVesselOperationPlaceService;

@Service
public class SmallVesselOperationPlaceServiceImpl extends AbstractJpaFilteringSortingServiceImpl<SmallVesselOperationPlace, Long, SmallVesselOperationPlaceRepository> implements SmallVesselOperationPlaceService{
    
    public SmallVesselOperationPlaceServiceImpl(SmallVesselOperationPlaceRepository rRepository) {
        super(rRepository);
    }

    @Override
    protected Predicate<SmallVesselOperationPlace> formTPredicate(String value) {
        Predicate<SmallVesselOperationPlace> predicate = m -> 
        {
            return m.getTitle().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<SmallVesselOperationPlace> getTComparator(String name, Direction dir) {
        return new Comparator<SmallVesselOperationPlace>() {
            @Override
            public int compare(SmallVesselOperationPlace o1, SmallVesselOperationPlace o2) {
                return 0;
            }
        };
    }

    @Override
    protected Comparator<SmallVesselOperationPlace> getTEmptyComparator() {
        return new Comparator<SmallVesselOperationPlace>() {
            @Override
            public int compare(SmallVesselOperationPlace o1, SmallVesselOperationPlace o2) {
                return 0;
            }
        };
    }

    @Override
    public List<SmallVesselOperationPlace> findAllByWaterBodyId(Long id) {
        return this.rRepository.findAllByWaterBodyId(id);
    }
    
    @Override
    public Page<SmallVesselOperationPlace> findAllByWaterBodyIdPagingRequest(Long id, PagingRequest pagingRequest) {
        List<SmallVesselOperationPlace> ts = rRepository.findAllByWaterBodyId(id);
       
        return getPage(ts, pagingRequest);
    }
}
