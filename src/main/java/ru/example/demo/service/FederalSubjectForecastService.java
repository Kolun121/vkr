package ru.example.demo.service;

import ru.example.demo.domain.FederalSubjectForecast;

import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;

public interface FederalSubjectForecastService extends JpaService<FederalSubjectForecast, Long> {
    FederalSubjectForecast findByFederalSubjectId(Long federalSubjectId);
    Page<FederalSubjectForecast> findAllByFederalSubjectIdPagingRequest(Long id, PagingRequest pagingRequest);
}