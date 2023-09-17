package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.member.model.request.MeBasePeriodRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindPeriodRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdatePeriodRequest;
import com.labreportapp.portalprojects.core.member.model.response.MePeriodResponse;
import com.labreportapp.portalprojects.entity.Period;
import com.labreportapp.portalprojects.infrastructure.projection.SimpleEntityProj;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MePeriodService {

    List<SimpleEntityProj> findAllSimpleEntity();

    List<MePeriodResponse> getAllPeriodByIdProject(MeFindPeriodRequest req,String idProject);

    PageableObject<MePeriodResponse> getAllPeriod(MeFindPeriodRequest req, String idProject);

    Period findById(String id);

    Period create(@Valid final MeBasePeriodRequest request);

    Period update(@Valid final MeUpdatePeriodRequest meUpdatePeriodRequest);

    Period checkDatePeriod(MeBasePeriodRequest request, String id);

    String delete(String id, String projectId);
}
