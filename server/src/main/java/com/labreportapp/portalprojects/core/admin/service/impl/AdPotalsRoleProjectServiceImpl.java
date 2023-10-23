package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleProjectRepository;
import com.labreportapp.portalprojects.core.admin.service.AdPotalsRoleProjectService;
import com.labreportapp.portalprojects.entity.RoleProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class AdPotalsRoleProjectServiceImpl implements AdPotalsRoleProjectService {

    @Autowired
    private AdPotalsRoleProjectRepository adPotalsRoleProjectRepository;

    @Override
    public List<RoleProject> getAllRoleProjectByProjId(String id) {
        return adPotalsRoleProjectRepository.findAllByProjectId(id);
    }
}
