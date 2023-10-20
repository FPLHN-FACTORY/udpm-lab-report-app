package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateTeamRequest;
import com.labreportapp.labreport.core.admin.model.response.AdTeamResponse;
import com.labreportapp.labreport.core.admin.repository.AdTeamRepository;
import com.labreportapp.labreport.core.admin.service.AdTeamService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.TeamFactory;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdTeamServiceImpl implements AdTeamService {

    @Autowired
    private AdTeamRepository adTeamRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdTeamResponse> adTeamResponseList;

    @Override
    public List<TeamFactory> findAllTeam(Pageable pageable) {
        return adTeamRepository.getAllTeam(pageable);
    }

    @Override
    public TeamFactory createTeam(AdCreateTeamRequest obj) {
        TeamFactory team = formUtils.convertToObject(TeamFactory.class, obj);
        return adTeamRepository.save(team);
    }

    @Override
    public TeamFactory updateTeam(AdUpdateTeamRequest obj) {
        Optional<TeamFactory> findById = adTeamRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        TeamFactory team = findById.get();
        team.setName(obj.getName());
        team.setDescriptions(obj.getDescriptions());

        return adTeamRepository.save(team);
    }

    @Override
    public PageableObject<AdTeamResponse> searchTeam(AdFindTeamRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdTeamResponse> adTeamResponses = adTeamRepository.searchTeam(rep, pageable);
        adTeamResponseList = adTeamResponses.stream().toList();
        return new PageableObject<>(adTeamResponses);
    }

    @Override
    public String deleteTeam(String id) {
        Optional<TeamFactory> findTeamById = adTeamRepository.findById(id);
        Integer countTeams = adTeamRepository.countMemberTeamByTeamId(id);
        if (!findTeamById.isPresent()) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        if (countTeams != null && countTeams > 0) {
            throw new RestApiException(Message.TEAM_MEMBER_TEAM_ALREADY_EXISTS);
        }
        adTeamRepository.delete(findTeamById.get());
        return id;
    }

    @Override
    public TeamFactory detailTeam(String id) {
        return adTeamRepository.findById(id).get();
    }
}
