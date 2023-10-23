package com.labreportapp.portalprojects.core.admin.service;

import com.labreportapp.portalprojects.entity.RoleProject;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface AdPotalsRoleProjectService {

    List<RoleProject> getAllRoleProjectByProjId(String id);
}
