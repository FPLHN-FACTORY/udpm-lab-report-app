package com.labreportapp.portalprojects.core.admin.service;

import com.labreportapp.portalprojects.core.admin.model.response.AdRoleMemberProjectDetailResponse;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface AdPotalsRoleMemberProjectService {

    List<AdRoleMemberProjectDetailResponse> getAllRoleMemberProjByIdProj(String idProject);
}
