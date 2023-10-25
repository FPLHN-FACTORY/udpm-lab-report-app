package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMembersInTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdCreateTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdDeleteListMemberTeamFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateTeamRequest;
import com.labreportapp.labreport.core.admin.model.response.AdAllMemberFactoryResponse;
import com.labreportapp.labreport.core.admin.model.response.AdTeamFactoryCustom;
import com.labreportapp.labreport.core.admin.model.response.AdTeamResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.TeamFactory;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdTeamService {

    List<TeamFactory> findAllTeam(Pageable pageable);

    TeamFactory createTeam(@Valid final AdCreateTeamRequest obj);

    TeamFactory updateTeam(final AdUpdateTeamRequest obj);

    PageableObject<AdTeamFactoryCustom> searchTeam(final AdFindTeamRequest rep);

    String deleteTeam(final String id);

    TeamFactory detailTeam(final String id);

    List<AdAllMemberFactoryResponse> detailMemberTeamFactory(String id);

    List<AdAllMemberFactoryResponse> getAllMemberFactory();

    List<String> addMembers(@Valid AdCreateMembersInTeamRequest request);

    String deleteMemberTeamFactory(String id);

    List<String> deleteListMemberTeamFactory(@Valid AdDeleteListMemberTeamFactoryRequest request);
}
