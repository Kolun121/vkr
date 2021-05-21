package ru.example.demo.service.springjpa;

import java.util.HashSet;
import ru.example.demo.service.CrudService;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public abstract class CrudServiceImpl<T, D, R extends CrudRepository<T, D>> implements CrudService<T, D> {
    private final R rRepository;
    
    public CrudServiceImpl(R rRepository) {
        this.rRepository = rRepository;
    }
    
    @Override
    public Iterable<T> findAll() {
        Set<T> objects = new HashSet<>();
        rRepository.findAll().forEach(objects::add);
        return objects;
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
    
}
