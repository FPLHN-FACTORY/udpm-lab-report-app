package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.service.AdPotalsRoleProjectService;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/admin/potals/role-project")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdPotalsRoleProjectController {

    @Autowired
    private AdPotalsRoleProjectService adPotalsRoleProjectService;

    @GetMapping("/project/{id}")
    public ResponseObject viewRoleByIdPro(@PathVariable("id") String id) {
        return new ResponseObject(adPotalsRoleProjectService.getAllRoleProjectByProjId(id));
    }
}
