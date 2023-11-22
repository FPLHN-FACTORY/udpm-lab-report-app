package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateRoleProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateRoleProjectRequest;
import com.labreportapp.portalprojects.core.member.service.MeRoleProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/member/role-project")
public class MeRoleProjectController {

    @Autowired
    private MeRoleProjectService meRoleProjectService;

    @GetMapping
    public ResponseObject getAllRoleProject(@RequestParam("idProject") String idProject) {
        return new ResponseObject(meRoleProjectService.getAllRoleProject(idProject));
    }

    @PostMapping
    public ResponseObject create(@RequestBody MeCreateRoleProjectRequest request) {
        return new ResponseObject(meRoleProjectService.create(request));
    }

    @PutMapping
    public ResponseObject update(@RequestBody MeUpdateRoleProjectRequest request) {
        return new ResponseObject(meRoleProjectService.update(request));
    }

    @DeleteMapping
    public ResponseObject delete(@RequestParam("id") String id) {
        return new ResponseObject(meRoleProjectService.delete(id));
    }
}
