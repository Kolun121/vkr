package ru.example.demo.service;

import ru.example.demo.domain.MunicipalityForecast;

import java.util.List;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;


public interface MunicipalityForecastService extends JpaService<MunicipalityForecast, Long> {
    List<MunicipalityForecast> findAllByMunicipalityId(Long id);
    Page<MunicipalityForecast> findAllByMunicipalityIdPagingRequest(Long id, PagingRequest pagingRequest);
}