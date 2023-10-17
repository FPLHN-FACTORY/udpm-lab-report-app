package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.response.AdRoleProjectResponse;
import com.labreportapp.labreport.core.admin.repository.AdRoleProjectRepository;
import com.labreportapp.labreport.core.admin.service.AdRoleProjectService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.portalprojects.entity.RoleProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdRoleProjectServiceImpl implements AdRoleProjectService {

    @Autowired
    private AdRoleProjectRepository adRoleProjectRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdRoleProjectResponse> adRoleProjectResponseList;

    @Override
    public List<RoleProject> findAllRoleProject(Pageable pageable) {
        return adRoleProjectRepository.getAllRoleProject(pageable);
    }

    @Override
    public RoleProject createRoleProject(AdCreateRoleProjectRequest obj) {
        RoleProject roleProject = formUtils.convertToObject(RoleProject.class, obj);
        return adRoleProjectRepository.save(roleProject);
    }

    @Override
    public RoleProject updateRoleProject(AdUpdateRoleProjectRequest obj) {
        Optional<RoleProject> findById = adRoleProjectRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.ROLE_PROJECT_NOT_EXISTS);
        }
        RoleProject roleProject = findById.get();
        roleProject.setName(obj.getName());
        roleProject.setDescription(obj.getDescription());
        roleProject.setProjectId(obj.getIdProject());

        return adRoleProjectRepository.save(roleProject);
    }

    @Override
    public PageableObject<AdRoleProjectResponse> searchRoleProject(AdFindRoleProjectRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdRoleProjectResponse> adRoleProjectResponses = adRoleProjectRepository.searchRoleProject(rep, pageable);
        adRoleProjectResponseList = adRoleProjectResponses.stream().toList();
        return new PageableObject<>(adRoleProjectResponses);
    }

    @Override
    public Boolean deleteRoleProject(String id) {
        Optional<RoleProject> findRoleProjectById = adRoleProjectRepository.findById(id);

        if (!findRoleProjectById.isPresent()) {
            throw new RestApiException(Message.ROLE_PROJECT_NOT_EXISTS);
        }

        adRoleProjectRepository.delete(findRoleProjectById.get());
        return true;
    }
}
