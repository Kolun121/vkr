package ru.example.demo.service;

import ru.example.demo.domain.CrowdedPlace;

import java.util.List;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;


public interface CrowdedPlaceService extends JpaService<CrowdedPlace, Long> {
    List<CrowdedPlace> findAllByWaterBodyId(Long id);
    Page<CrowdedPlace> findAllByWaterBodyIdPagingRequest(Long id, PagingRequest pagingRequest);
}