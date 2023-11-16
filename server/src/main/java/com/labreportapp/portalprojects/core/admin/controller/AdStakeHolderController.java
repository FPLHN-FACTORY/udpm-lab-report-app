package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateStakeholderRequest;
import com.labreportapp.portalprojects.core.admin.service.AdStakeholderProjectService;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/stakeholder")
public class AdStakeHolderController {

    @Autowired
    private AdStakeholderProjectService stakeHolderService;

    @GetMapping("/{stakeholderId}")
    public ResponseObject getProjectsByStakeholderId(@PathVariable ("stakeholderId") String stakeholderId) {
        return new ResponseObject(stakeHolderService.getProjectsByStakeholderId(stakeholderId));
    }

    @GetMapping("/projectbystakeIdNull")
    public ResponseObject getProjectsByStakeholderId() {
        return new ResponseObject(stakeHolderService.getAllProjects());
    }

    @PutMapping("/updateStakeHolderByIdNull/{id}")
    public ResponseObject update(@PathVariable("id") String id, @RequestBody AdUpdateStakeholderRequest request) {
        request.setId(id);
        return new ResponseObject(stakeHolderService.updateStakeHolder(request));
    }
}
