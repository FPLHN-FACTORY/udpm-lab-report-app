package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.portalprojects.core.admin.model.response.AdRoleMemberProjectDetailResponse;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleMemberProjectRepository;
import com.labreportapp.portalprojects.core.admin.service.AdPotalsRoleMemberProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class AdPotalsRoleMemberProjectServiceImpl implements AdPotalsRoleMemberProjectService {

    @Autowired
    private AdPotalsRoleMemberProjectRepository adPotalsRoleMemberProjectRepository;

    @Override
    public List<AdRoleMemberProjectDetailResponse> getAllRoleMemberProjByIdProj(String idProject) {
        return adPotalsRoleMemberProjectRepository.getAllRoleMemberCustomByIdProj(idProject);
    }
}
