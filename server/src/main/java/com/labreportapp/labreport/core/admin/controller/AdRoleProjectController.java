package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreateRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.response.AdRoleProjectResponse;
import com.labreportapp.labreport.core.admin.service.AdRoleProjectService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.entity.RoleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/role-config")
public class AdRoleProjectController {

    @Autowired
    private AdRoleProjectService adRoleProjectService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllRoleProject(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<RoleConfig> RoleProjectList = adRoleProjectService.findAllRoleProject(pageResquest);
        return ResponseEntity.ok(RoleProjectList);
    }

    @GetMapping("")
    public ResponseObject viewRoleProject(@ModelAttribute final AdFindRoleProjectRequest request) {
        return new ResponseObject((adRoleProjectService.searchRoleProject(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchRoleProject(final AdFindRoleProjectRequest request) {
        PageableObject<AdRoleProjectResponse> listRoleProject = adRoleProjectService.searchRoleProject(request);
        return new ResponseObject(listRoleProject);
    }

    @PostMapping("/add")
    public ResponseObject addRoleProject(@RequestBody AdCreateRoleProjectRequest obj) {
        return new ResponseObject(adRoleProjectService.createRoleProject(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteRoleProject(@PathVariable("id") String id) {
        return new ResponseObject(adRoleProjectService.deleteRoleProject(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateRoleProject(@PathVariable("id") String id,
                                            @RequestBody AdUpdateRoleProjectRequest obj) {
        obj.setId(id);
        return new ResponseObject(adRoleProjectService.updateRoleProject(obj));
    }
}
