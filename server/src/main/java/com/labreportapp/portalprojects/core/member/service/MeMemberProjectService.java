package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.member.model.request.MeListMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeMemberProjectResponse;
import com.labreportapp.portalprojects.entity.MemberProject;
import jakarta.validation.Valid;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeMemberProjectService {

    List<MeMemberProjectResponse> getAllMemberProject(String idProject);

    MemberProject update(@Valid MeUpdateMemberProjectRequest request, StompHeaderAccessor headerAccessor);

    List<MemberProject> create(@Valid MeListMemberProjectRequest request, StompHeaderAccessor headerAccessor);
}
