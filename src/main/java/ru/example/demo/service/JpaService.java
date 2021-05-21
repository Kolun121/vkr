package ru.example.demo.service;

import java.util.List;

public interface JpaService<T, ID> extends CrudService<T, ID>{
    
    public List<T> findAll();
}
