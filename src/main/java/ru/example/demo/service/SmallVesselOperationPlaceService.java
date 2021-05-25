package ru.example.demo.service;

import ru.example.demo.domain.SmallVesselOperationPlace;

import java.util.List;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;


public interface SmallVesselOperationPlaceService extends JpaService<SmallVesselOperationPlace, Long> {
    List<SmallVesselOperationPlace> findAllByWaterBodyId(Long id);
    Page<SmallVesselOperationPlace> findAllByWaterBodyIdPagingRequest(Long id, PagingRequest pagingRequest);
}