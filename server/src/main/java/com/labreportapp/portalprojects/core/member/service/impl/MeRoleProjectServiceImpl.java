package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.labreport.infrastructure.constant.RoleDefault;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateRoleProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateRoleProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeRoleProjectResponse;
import com.labreportapp.portalprojects.core.member.repository.MeRoleProjectRepository;
import com.labreportapp.portalprojects.core.member.service.MeRoleProjectService;
import com.labreportapp.portalprojects.entity.RoleProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeRoleProjectServiceImpl implements MeRoleProjectService {

    @Autowired
    private MeRoleProjectRepository meRoleProjectRepository;

    @Override
    public List<MeRoleProjectResponse> getAllRoleProject(String idProject) {
        return meRoleProjectRepository.getAllRoleProject(idProject);
    }

    @Override
    public RoleProject create(@Valid MeCreateRoleProjectRequest request) {
        if (request.getRoleDefault() == 0 && meRoleProjectRepository.getRoleProjectDefault(request.getProjectId()) != null) {
            throw new RestApiException(Message.ROLE_CONFIG_ONLY_HAVA_ONE);
        }
        RoleProject roleProject = new RoleProject();
        roleProject.setRoleDefault(request.getRoleDefault() == 0 ? RoleDefault.DEFAULT : RoleDefault.NO_DEFAULT);
        roleProject.setDescription(request.getDescription());
        roleProject.setName(request.getName());
        roleProject.setProjectId(request.getProjectId());
        return meRoleProjectRepository.save(roleProject);
    }

    @Override
    public RoleProject update(@Valid MeUpdateRoleProjectRequest obj) {
        Optional<RoleProject> findById = meRoleProjectRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.ROLE_PROJECT_NOT_EXISTS);
        }
        if (findById.get().getRoleDefault() == RoleDefault.NO_DEFAULT) {
            if (obj.getRoleDefault() == 0 && meRoleProjectRepository.getRoleProjectDefault(findById.get().getProjectId()) != null) {
                throw new RestApiException(Message.ROLE_CONFIG_ONLY_HAVA_ONE);
            }
        }
        RoleProject roleProject = findById.get();
        roleProject.setName(obj.getName());
        roleProject.setDescription(obj.getDescription());
        roleProject.setRoleDefault(obj.getRoleDefault() == 0 ? RoleDefault.DEFAULT : RoleDefault.NO_DEFAULT);
        return meRoleProjectRepository.save(roleProject);
    }

    @Override
    public Boolean delete(String id) {
        Optional<RoleProject> findRoleProject = meRoleProjectRepository.findById(id);
        if (!findRoleProject.isPresent()) {
            throw new RestApiException(Message.ROLE_MEMBER_NOT_EXISTS);
        }
        Integer countRoles = meRoleProjectRepository.countRoleMemberProject(id);
        if (countRoles != null && countRoles > 0) {
            throw new RestApiException(Message.ROLE_FACTORY_HAVE_MEMBER);
        }
        meRoleProjectRepository.delete(findRoleProject.get());
        return true;
    }
}
