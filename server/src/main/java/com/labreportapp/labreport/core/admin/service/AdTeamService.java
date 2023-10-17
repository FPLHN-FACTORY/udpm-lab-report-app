package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdTeamResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Team;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdTeamService {
    List<Team> findAllTeam(Pageable pageable);

    Team createTeam(@Valid final AdCreateTeamRequest obj);

    Team updateTeam(final AdUpdateTeamRequest obj);

    PageableObject<AdTeamResponse> searchTeam(final AdFindTeamRequest rep);

    Boolean deleteTeam(final String id);
}
