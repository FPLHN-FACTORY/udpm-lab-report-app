package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectRoleRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.core.admin.service.AdProjectService;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.entity.Project;
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

import java.text.ParseException;
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
    public ResponseObject viewProject(@ModelAttribute final AdFindProjectRequest repuest) throws ParseException {
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

    @PutMapping("/{id}")
    public ResponseObject updateProject(@PathVariable("id") String id,
                                        @RequestBody AdUpdateProjectRoleRequest request) {
        return new ResponseObject(adProjectService.updateProject(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseObject removeProject(@PathVariable("id") String id) {
        return new ResponseObject(adProjectService.removeProject(id));
    }
}
