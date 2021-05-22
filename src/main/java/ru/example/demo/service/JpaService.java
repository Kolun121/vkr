package ru.example.demo.service;

import java.util.List;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;

public interface JpaService<T, ID> extends CrudService<T, ID>{
    
    public List<T> findAll();
    Page<T> findAllPagingRequest(PagingRequest pagingRequest);
}
