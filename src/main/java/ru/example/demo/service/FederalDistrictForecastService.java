package ru.example.demo.service;

import java.util.List;
import ru.example.demo.domain.FederalDistrictForecast;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;


public interface FederalDistrictForecastService extends JpaService<FederalDistrictForecast, Long> {
    List<FederalDistrictForecast> findAllByFederalDistrictId(Long id);
    Page<FederalDistrictForecast> findAllByFederalDistrictIdPagingRequest(Long id, PagingRequest pagingRequest);
}