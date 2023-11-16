package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreateMemberProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateMemberProjectRequest;
import com.labreportapp.portalprojects.core.admin.service.AdMemberProjectService;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/admin/member-project")
public class AdMemberProjectController {

    @Autowired
    private AdMemberProjectService adMemberProjectService;

    @GetMapping()
    public ResponseObject view() {
        return new ResponseObject(adMemberProjectService.getAll());
    }

    @GetMapping("/search")
    public ResponseObject search(final AdFindProjectRequest rep) {
        return new ResponseObject(adMemberProjectService.searchProject(rep));
    }

    @GetMapping("/list-member-project/{id}")
    public ResponseObject findAllMemberJoinProject(@PathVariable("id") String idProject) {
        return new ResponseObject(adMemberProjectService.findAllMemberJoinProject(idProject));
    }

    @PostMapping
    public ResponseObject addMemberProject(@RequestBody AdCreateMemberProjectRequest cmd) {
        return new ResponseObject(adMemberProjectService.createMemberProject(cmd));
    }

    @PostMapping("/add-all")
    public ResponseObject addListMemberProject(@RequestBody List<AdCreateMemberProjectRequest> cmd) {
        return new ResponseObject(adMemberProjectService.createListMemberProject(cmd));
    }

    @PutMapping("/{id}")
    public ResponseObject updateMemberProjcet(@PathVariable("id") String id,
                                              @RequestBody AdUpdateMemberProjectRequest cmd) {
        cmd.setId(id);
        return new ResponseObject(adMemberProjectService.updateMemberProject(cmd));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteMemberProject(@PathVariable("id") String id) {
        return new ResponseObject(adMemberProjectService.delete(id));
    }

    @GetMapping("/get-one")
    public ResponseObject getOne(@RequestParam("idProject") String idProject,
                                 @RequestParam("idMember") String idMember) {
        return new ResponseObject(adMemberProjectService.getOne(idMember, idProject));
    }

}
