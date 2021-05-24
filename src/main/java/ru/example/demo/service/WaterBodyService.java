package ru.example.demo.service;

import ru.example.demo.domain.WaterBody;

import java.util.List;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;


public interface WaterBodyService extends JpaService<WaterBody, Long> {
    List<WaterBody> findAllByMunicipalityId(Long id);
    Page<WaterBody> findAllByMunicipalityIdPagingRequest(Long id, PagingRequest pagingRequest);
}