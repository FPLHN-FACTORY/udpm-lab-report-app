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
import com.labreportapp.labreport.util.LoggerUtil;
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

    @Autowired
    private LoggerUtil loggerUtil;

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
        loggerUtil.sendLogScreen("Đã thêm vai trò trong xưởng mới: " + roleFactory.getName() + ".", "");
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật vai trò trong xưởng có tên " + findById.get().getName()+": ");
        if (!findById.get().getName().equals(obj.getName())) {
            stringBuilder.append(" tên của vai trò từ ").append(findById.get().getName()).append(" thành ").append(obj.getName()).append(",");
        }
        if (!findById.get().getDescriptions().equals(obj.getDescriptions())) {
            stringBuilder.append(" mô tả của vai trò từ ").append(findById.get().getDescriptions()).append(" thành ").append(obj.getDescriptions()).append(",");
        }
        if (!findById.get().getRoleDefault().equals(RoleDefault.values()[obj.getRoleDefault()])) {
            stringBuilder.append(" quyền mặc định từ ").append(findById.get().getRoleDefault()).append(" thành ").append(RoleDefault.values()[obj.getRoleDefault()]).append(",");
        }
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.setCharAt(stringBuilder.length() - 1, '.');
        }
        loggerUtil.sendLogScreen(stringBuilder.toString(), "");
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
        loggerUtil.sendLogScreen("Đã xóa vai trò trong xưởng có tên là " + findRoleFactoryById.get().getName() + ".", "");
        adRoleFactoryRepository.delete(findRoleFactoryById.get());
        return true;
    }
}
