package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdRoleFactoryResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.RoleFactory;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdRoleFactoryService {
    List<RoleFactory> findAllRoleFactory(Pageable pageable);

    RoleFactory createRoleFactory(@Valid final AdCreateRoleFactoryRequest obj);

    RoleFactory updateRoleFactory(final AdUpdateRoleFactoryRequest obj);

    PageableObject<AdRoleFactoryResponse> searchRoleFactory(final AdFindRoleFactoryRequest rep);

    Boolean deleteRoleFactory(final String id);
}
