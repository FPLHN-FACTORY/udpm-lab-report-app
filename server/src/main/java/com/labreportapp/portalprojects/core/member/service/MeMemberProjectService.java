package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.portalprojects.core.member.model.request.MeListMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeMemberProjectCustom;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateMemberProjectRequest;
import com.labreportapp.portalprojects.entity.MemberProject;
import jakarta.validation.Valid;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeMemberProjectService {

    List<MeMemberProjectCustom> getAllMemberProject(String idProject);

    List<SimpleResponse> getAllMemberTeam(String idProject);

    MemberProject update(@Valid MeUpdateMemberProjectRequest request, StompHeaderAccessor headerAccessor);

    List<MemberProject> create(@Valid MeListMemberProjectRequest request, StompHeaderAccessor headerAccessor);
}
