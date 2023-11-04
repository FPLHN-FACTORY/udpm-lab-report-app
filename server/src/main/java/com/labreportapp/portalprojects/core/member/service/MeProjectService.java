package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.member.model.request.MeFindProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateBackgroundProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateFiledProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeDetailUpdateProjectResponse;
import com.labreportapp.portalprojects.core.member.model.response.MeProjectResponse;
import com.labreportapp.portalprojects.entity.Project;
import jakarta.validation.Valid;

/**
 * @author thangncph26123
 */
public interface MeProjectService {

    PageableObject<MeProjectResponse> getAllProjectByIdUser(MeFindProjectRequest request);

    Project findById(String id);

    Project updateBackground(@Valid MeUpdateBackgroundProjectRequest request);

    MeDetailUpdateProjectResponse detailPeriodToProject(String id);

    MeDetailUpdateProjectResponse updateFiledProject(@Valid MeUpdateFiledProjectRequest request);
}
