package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.service.AdPotalsRoleConfigService;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/admin/potals/role-config")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdPotalsRoleConfigController {

    @Autowired
    private AdPotalsRoleConfigService adRoleConfigManagementService;

    @GetMapping("")
    public ResponseObject showAllRoleConfig() {
        return new ResponseObject(adRoleConfigManagementService.getAll());
    }

}
