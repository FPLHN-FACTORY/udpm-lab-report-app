package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.student.model.request.StFindTeamFactoryRequest;
import com.labreportapp.labreport.core.student.model.response.StAllMemberFactoryResponse;
import com.labreportapp.labreport.core.student.model.response.StTeamFactoryCustom;
import com.labreportapp.labreport.entity.TeamFactory;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StTeamFactoryService {

    List<TeamFactory> findAllTeam(Pageable pageable);

    PageableObject<StTeamFactoryCustom> searchTeam(final StFindTeamFactoryRequest rep);

    TeamFactory detailTeam(final String id);

    List<StAllMemberFactoryResponse> detailMemberTeamFactory(String id);

    List<StAllMemberFactoryResponse> getAllMemberFactory();
}
