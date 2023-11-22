package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.service.AdPotalsMemberFactoryService;
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
@RequestMapping("/api/admin/potals/member-factory")
public class AdPotalsMemberFactoryController {

    @Autowired
    private AdPotalsMemberFactoryService adPotalsMemberFactoryService;

    @GetMapping("")
    public ResponseObject showAllRoleConfig() {
        return new ResponseObject(adPotalsMemberFactoryService.getAllMemberFactory());
    }
}
