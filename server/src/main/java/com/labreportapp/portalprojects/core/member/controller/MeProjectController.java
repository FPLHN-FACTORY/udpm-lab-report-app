package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.admin.service.AdCategoryService;
import com.labreportapp.portalprojects.core.admin.service.AdGroupProjectService;
import com.labreportapp.portalprojects.core.admin.service.AdProjectService;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.MeFindProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateBackgroundProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateFiledProjectRequest;
import com.labreportapp.portalprojects.core.member.service.MeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/member/project")
public class MeProjectController {

    @Autowired
    private MeProjectService meProjectService;

    @Autowired
    private AdCategoryService adCategoryService;

    @Autowired
    private AdProjectService adProjectService;

    @Autowired
    private AdGroupProjectService adGroupProjectService;

    @GetMapping
    public ResponseObject getProjectByIdUser(final MeFindProjectRequest request) {
        return new ResponseObject(meProjectService.getAllProjectByIdUser(request));
    }

    @GetMapping("/detail-project/{id}")
    public ResponseObject detailProject(@PathVariable("id") String id) {
        return new ResponseObject(meProjectService.findById(id));
    }

    @GetMapping("/detail-project-custom/{id}")
    public ResponseObject detailProjectAndCustomPeriodProject(@PathVariable("id") String id) {
        return new ResponseObject(meProjectService.detailPeriodToProject(id));
    }

    @PutMapping("/detail-project-custom")
    public ResponseObject updateFiledProjectAndCustomPeriodProject(@RequestBody MeUpdateFiledProjectRequest request) {
        return new ResponseObject(meProjectService.updateFiledProject(request));
    }

    @MessageMapping("/update-background-project/{projectId}")
    @SendTo("/portal-projects/update-background-project/{projectId}")
    public ResponseObject updateBackGround(@RequestBody MeUpdateBackgroundProjectRequest request,
                                           @DestinationVariable String projectId) {
        return new ResponseObject(meProjectService.updateBackground(request));
    }

    @GetMapping("/list")
    public ResponseObject findAll() {
        return new ResponseObject(adCategoryService.findAll());
    }

    @GetMapping("/detail-update/{id}")
    public ResponseObject detailUpdateProject(@PathVariable("id") String id) {
        return new ResponseObject(adProjectService.detailUpdate(id));
    }

    @GetMapping("/get-all")
    public ResponseObject getAllGroupToProjectManagement() {
        return new ResponseObject(adGroupProjectService.getAllGroupToProjectManagement());
    }
}
