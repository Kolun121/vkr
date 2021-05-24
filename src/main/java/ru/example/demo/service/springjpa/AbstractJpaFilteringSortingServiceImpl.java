package ru.example.demo.service.springjpa;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import ru.example.demo.service.JpaService;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.helper.AbstractSortingAndFiltering;
import ru.example.demo.helper.paging.Direction;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;


public abstract class AbstractJpaFilteringSortingServiceImpl <T, D, R extends JpaRepository<T, D>>
        extends AbstractSortingAndFiltering<T>
        implements JpaService<T, D> 
{
    
    protected final R rRepository;

    public AbstractJpaFilteringSortingServiceImpl(R rRepository) {
        this.rRepository = rRepository;
    }
   
    public List<T> findAll(){
        return rRepository.findAll();
    }    

    @Override
    public T findById(D id) {
        Optional<T> tOptional = rRepository.findById(id);
        
        if (!tOptional.isPresent()) {
            return null;
        }

        return tOptional.get();
    }

    @Override
    public T save(T object) {
        return rRepository.save(object);
    }

    @Override
    public void delete(T object) {
        rRepository.delete(object);
    }

    @Override
    public void deleteById(D id) {
        rRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Iterable<T> objects) {
        rRepository.deleteAll(objects);
    }
    
    @Override
    public Page<T> findAllPagingRequest(PagingRequest pagingRequest) {
        List<T> ts = rRepository.findAll();
       
        return getPage(ts, pagingRequest);
    }
    
    @Override
    public Iterable<T> saveAll(Iterable<T> itrbl) {
        
        Iterable<T> ts = rRepository.saveAll(itrbl);
       
        return ts;
    } 
    
}
