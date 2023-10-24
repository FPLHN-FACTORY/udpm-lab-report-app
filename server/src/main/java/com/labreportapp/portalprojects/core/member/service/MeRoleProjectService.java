package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.member.model.request.MeCreateRoleProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateRoleProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeRoleProjectResponse;
import com.labreportapp.portalprojects.entity.RoleProject;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeRoleProjectService {

    List<MeRoleProjectResponse> getAllRoleProject(String idProject);

    RoleProject create(@Valid MeCreateRoleProjectRequest request);

    RoleProject update(@Valid MeUpdateRoleProjectRequest obj);

    Boolean delete(final String id);
}
