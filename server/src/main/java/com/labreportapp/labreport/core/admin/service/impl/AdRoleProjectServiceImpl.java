package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.response.AdRoleProjectResponse;
import com.labreportapp.labreport.core.admin.repository.AdRoleProjectRepository;
import com.labreportapp.labreport.core.admin.service.AdRoleProjectService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.infrastructure.constant.RoleDefault;
import com.labreportapp.portalprojects.entity.RoleConfig;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
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

    @Override
    public List<RoleConfig> findAllRoleProject(Pageable pageable) {
        return adRoleProjectRepository.getAllRoleProject(pageable);
    }

    @Override
    public RoleConfig createRoleProject(@Valid AdCreateRoleProjectRequest obj) {
        if (obj.getRoleDefault() == 0 && adRoleProjectRepository.getRoleConfigDefault() != null) {
            throw new RestApiException(Message.ROLE_CONFIG_ONLY_HAVA_ONE);
        }
        RoleConfig roleConfig = new RoleConfig();
        roleConfig.setRoleDefault(obj.getRoleDefault() == 0 ? RoleDefault.DEFAULT : RoleDefault.NO_DEFAULT);
        roleConfig.setDescription(obj.getDescription());
        roleConfig.setName(obj.getName());
        return adRoleProjectRepository.save(roleConfig);
    }


    @Override
    public RoleConfig updateRoleProject(@Valid AdUpdateRoleProjectRequest obj) {
        Optional<RoleConfig> findById = adRoleProjectRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.ROLE_PROJECT_NOT_EXISTS);
        }
        if (findById.get().getRoleDefault() == RoleDefault.NO_DEFAULT) {
            if (obj.getRoleDefault() == 0 && adRoleProjectRepository.getRoleConfigDefault() != null) {
                throw new RestApiException(Message.ROLE_CONFIG_ONLY_HAVA_ONE);
            }
        }
        RoleConfig roleConfig = findById.get();
        roleConfig.setName(obj.getName());
        roleConfig.setDescription(obj.getDescription());
        roleConfig.setRoleDefault(obj.getRoleDefault() == 0 ? RoleDefault.DEFAULT : RoleDefault.NO_DEFAULT);
        return adRoleProjectRepository.save(roleConfig);
    }

    @Override
    public PageableObject<AdRoleProjectResponse> searchRoleProject(AdFindRoleProjectRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdRoleProjectResponse> adRoleProjectResponses = adRoleProjectRepository.searchRoleProject(rep, pageable);
        return new PageableObject<>(adRoleProjectResponses);
    }

    @Override
    public Boolean deleteRoleProject(String id) {
        Optional<RoleConfig> findRoleProjectById = adRoleProjectRepository.findById(id);
        if (!findRoleProjectById.isPresent()) {
            throw new RestApiException(Message.ROLE_PROJECT_NOT_EXISTS);
        }
        adRoleProjectRepository.delete(findRoleProjectById.get());
        return true;
    }
}
