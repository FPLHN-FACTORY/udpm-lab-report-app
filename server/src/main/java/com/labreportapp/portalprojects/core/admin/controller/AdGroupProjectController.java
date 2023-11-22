package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateTitleGroupProject;
import com.labreportapp.portalprojects.core.admin.service.AdGroupProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/admin/group-project")
public class AdGroupProjectController {

    @Autowired
    private AdGroupProjectService adGroupProjectService;

    @GetMapping
    public ResponseObject getAllPage(final AdFindGroupProjectRequest request) {
        return new ResponseObject(adGroupProjectService.getAllPage(request));
    }

    @PutMapping
    public ResponseObject updateGroupProject(@Valid @ModelAttribute AdUpdateGroupProjectRequest request) throws IOException {
        return new ResponseObject(adGroupProjectService.updateGroupProject(request));
    }

    @PostMapping
    public ResponseObject createGroupProject(@Valid @ModelAttribute AdCreateGroupProjectRequest request) throws IOException {
        return new ResponseObject(adGroupProjectService.createGroupProject(request));
    }

    @GetMapping("/detail")
    public ResponseObject detailGroupProject(@RequestParam("id") String id) {
        return new ResponseObject(adGroupProjectService.detailGroupProject(id));
    }

    @GetMapping("/get-all-project")
    public ResponseObject getAllProject(@RequestParam("id") String id) {
        return new ResponseObject(adGroupProjectService.getAllProject(id));
    }

    @GetMapping("/get-all")
    public ResponseObject getAllGroupToProjectManagement() {
        return new ResponseObject(adGroupProjectService.getAllGroupToProjectManagement());
    }

    @PutMapping("/title")
    public ResponseObject updateNameDescriptionGroupProject(@Valid @RequestBody AdUpdateTitleGroupProject request) {
        return new ResponseObject(adGroupProjectService.updateTitleGroupProject(request));
    }

}
