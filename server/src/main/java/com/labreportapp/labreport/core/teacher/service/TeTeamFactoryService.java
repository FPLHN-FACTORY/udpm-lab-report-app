package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindTeamFactoryRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeAllMemberFactoryResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamFactoryCustom;
import com.labreportapp.labreport.entity.TeamFactory;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface TeTeamFactoryService {

    List<TeamFactory> findAllTeam(Pageable pageable);

    PageableObject<TeTeamFactoryCustom> searchTeam(final TeFindTeamFactoryRequest rep);

    TeamFactory detailTeam(final String id);

    List<TeAllMemberFactoryResponse> detailMemberTeamFactory(String id);

    List<TeAllMemberFactoryResponse> getAllMemberFactory();


}
