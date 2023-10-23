package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.core.admin.service.AdProjectService;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/admin/project")
@CrossOrigin(origins = {"*"})
public class AdProjectController {

    @Autowired
    private AdProjectService adProjectService;

    @GetMapping("page/{page}")
    public ResponseEntity<?> fintAll(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<Project> list = adProjectService.findAllProject(pageResquest);
        return ResponseEntity.ok(list);
    }

    @GetMapping("")
    public ResponseObject viewProject(@ModelAttribute final AdFindProjectRequest repuest) {
        return new ResponseObject((adProjectService.searchProject(repuest)));
    }

    @GetMapping("/{id}")
    public ResponseObject detailProject(@PathVariable("id") String id) {
        return new ResponseObject(adProjectService.findProjectById(id));
    }

    @GetMapping("/detail-update/{id}")
    public ResponseObject detailUpdateProject(@PathVariable("id") String id) {
        return new ResponseObject(adProjectService.detailUpdate(id));
    }

    @GetMapping("/search")
    public ResponseObject searchProjce(final AdFindProjectRequest repuest) {
        PageableObject<AdProjectReponse> listProjce = adProjectService.searchProject(repuest);
        return new ResponseObject(listProjce);
    }

    @PostMapping
    public ResponseObject addProject(@RequestBody AdCreateProjectRequest cmd) {
        return new ResponseObject(adProjectService.createProject(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject removeProject(@PathVariable("id") String id) {
        return new ResponseObject(adProjectService.removeProject(id));
    }

    @PutMapping("/{id}")
    public ResponseObject updateProjcet(@PathVariable("id") String id,
                                        @RequestBody AdUpdateProjectRequest cmd) {
        cmd.setId(id);
        return new ResponseObject(adProjectService.updateProject(cmd));
    }
}
