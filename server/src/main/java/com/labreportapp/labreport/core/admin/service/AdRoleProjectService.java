package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdRoleProjectResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.RoleConfig;
import com.labreportapp.portalprojects.entity.RoleProject;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdRoleProjectService {

    List<RoleConfig> findAllRoleProject(Pageable pageable);

    RoleConfig createRoleProject(@Valid final AdCreateRoleProjectRequest obj);

    RoleConfig updateRoleProject(@Valid AdUpdateRoleProjectRequest obj);

    PageableObject<AdRoleProjectResponse> searchRoleProject(final AdFindRoleProjectRequest rep);

    Boolean deleteRoleProject(final String id);
}
