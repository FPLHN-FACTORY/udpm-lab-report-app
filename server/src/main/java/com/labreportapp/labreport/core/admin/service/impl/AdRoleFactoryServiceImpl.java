package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateRoleFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindRoleFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateRoleFactoryRequest;
import com.labreportapp.labreport.core.admin.model.response.AdRoleFactoryResponse;
import com.labreportapp.labreport.core.admin.repository.AdRoleFactoryRepository;
import com.labreportapp.labreport.core.admin.service.AdRoleFactoryService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.RoleFactory;
import com.labreportapp.labreport.infrastructure.constant.RoleDefault;
import com.labreportapp.labreport.util.FormUtils;
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
public class AdRoleFactoryServiceImpl implements AdRoleFactoryService {

    @Autowired
    private AdRoleFactoryRepository adRoleFactoryRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdRoleFactoryResponse> adRoleFactoryResponseList;

    @Override
    public List<RoleFactory> findAllRoleFactory(Pageable pageable) {
        return adRoleFactoryRepository.getAllRoleFactory(pageable);
    }

    @Override
    public RoleFactory createRoleFactory(@Valid AdCreateRoleFactoryRequest obj) {
        if (obj.getRoleDefault() == 0 && adRoleFactoryRepository.getRoleConfigDefault() != null) {
            throw new RestApiException(Message.ROLE_CONFIG_ONLY_HAVA_ONE);
        }
        RoleFactory roleFactory = new RoleFactory();
        roleFactory.setRoleDefault(obj.getRoleDefault() == 0 ? RoleDefault.DEFAULT : RoleDefault.NO_DEFAULT);
        roleFactory.setDescriptions(obj.getDescriptions());
        roleFactory.setName(obj.getName());
        return adRoleFactoryRepository.save(roleFactory);
    }

    @Override
    public RoleFactory updateRoleFactory(@Valid AdUpdateRoleFactoryRequest obj) {
        Optional<RoleFactory> findById = adRoleFactoryRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.ROLE_FACTORY_NOT_EXISTS);
        }
        if (findById.get().getRoleDefault() == RoleDefault.NO_DEFAULT) {
            if (obj.getRoleDefault() == 0 && adRoleFactoryRepository.getRoleConfigDefault() != null) {
                throw new RestApiException(Message.ROLE_CONFIG_ONLY_HAVA_ONE);
            }
        }
        RoleFactory roleFactory = findById.get();
        roleFactory.setName(obj.getName());
        roleFactory.setDescriptions(obj.getDescriptions());
        roleFactory.setRoleDefault(obj.getRoleDefault() == 0 ? RoleDefault.DEFAULT : RoleDefault.NO_DEFAULT);
        return adRoleFactoryRepository.save(roleFactory);
    }

    @Override
    public PageableObject<AdRoleFactoryResponse> searchRoleFactory(AdFindRoleFactoryRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdRoleFactoryResponse> adRoleFactoryResponses = adRoleFactoryRepository.searchRoleFactory(rep, pageable);
        adRoleFactoryResponseList = adRoleFactoryResponses.stream().toList();
        return new PageableObject<>(adRoleFactoryResponses);
    }

    @Override
    public Boolean deleteRoleFactory(String id) {
        Optional<RoleFactory> findRoleFactoryById = adRoleFactoryRepository.findById(id);
        if (!findRoleFactoryById.isPresent()) {
            throw new RestApiException(Message.ROLE_MEMBER_NOT_EXISTS);
        }
        Integer countRoles = adRoleFactoryRepository.countMemberFactoryByRoleId(id);
        if (countRoles != null && countRoles > 0) {
            throw new RestApiException(Message.ROLE_FACTORY_HAVE_MEMBER);
        }
        adRoleFactoryRepository.delete(findRoleFactoryById.get());
        return true;
    }
}
