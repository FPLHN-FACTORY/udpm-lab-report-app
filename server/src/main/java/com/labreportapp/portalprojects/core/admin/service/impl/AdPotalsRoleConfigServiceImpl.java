package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleConfigRepository;
import com.labreportapp.portalprojects.core.admin.service.AdPotalsRoleConfigService;
import com.labreportapp.portalprojects.entity.RoleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class AdPotalsRoleConfigServiceImpl implements AdPotalsRoleConfigService {

    @Autowired
    private AdPotalsRoleConfigRepository adRoleConfigManagementRepository;

    @Override
    public List<RoleConfig> getAll() {
        return adRoleConfigManagementRepository.findAll(Sort.by("name"));
    }
}
