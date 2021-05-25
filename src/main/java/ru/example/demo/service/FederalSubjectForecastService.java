package ru.example.demo.service;

import ru.example.demo.domain.FederalSubjectForecast;

import java.util.List;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;

public interface FederalSubjectForecastService extends JpaService<FederalSubjectForecast, Long> {
    List<FederalSubjectForecast> findAllByFederalSubjectId(Long id);
    Page<FederalSubjectForecast> findAllByFederalSubjectIdPagingRequest(Long id, PagingRequest pagingRequest);
}