package com.labreportapp.portalprojects.core.admin.service;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectRoleRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdDetailProjectCateMemberRespone;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.Project;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author hieundph25894
 */

public interface AdProjectService {

    List<Project> findAllProject(Pageable pageable);

    AdProjectReponse createProject(@Valid final AdCreateProjectRequest request);

    AdProjectReponse updateProject(@Valid AdUpdateProjectRoleRequest request, String idProject);

    AdDetailProjectCateMemberRespone detailUpdate(String idProject);

    PageableObject<AdProjectReponse> searchProject(final AdFindProjectRequest rep);

    Project findProjectById(final String id);

    Boolean removeProject(final String id);

}
