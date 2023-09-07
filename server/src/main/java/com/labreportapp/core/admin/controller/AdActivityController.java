package com.labreportapp.core.admin.controller;

import com.labreportapp.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.core.admin.model.response.AdActivityResponse;
import com.labreportapp.core.admin.service.AdActivityService;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.entity.Activity;
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

@RestController
@RequestMapping("/admin/activity")
@CrossOrigin(origins = {"*"})
public class AdActivityController {

    @Autowired
    private AdActivityService adActivityService;

    @GetMapping("")
    public ResponseObject viewActivity(final AdFindActivityRequest request){

        return new ResponseObject(adActivityService.searchActivity(request));
    }

    @PostMapping
    public ResponseObject creatActivity(@RequestBody final AdCreatActivityRequest request){
        return new ResponseObject(adActivityService.creatActivity(request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteActivity(@PathVariable("id") String id){
        return new ResponseObject(adActivityService.deleteActivity(id));
    }

    @PutMapping("/{id}")
    public ResponseObject updateActivity(@PathVariable("id") String id, @RequestBody AdUpdateActivityRequest request){
        request.setId(id);
        return new ResponseObject(adActivityService.updateActivity(request));
    }

    @GetMapping("/{id}")
    public ResponseObject getOne(@PathVariable("id") String id){
        return new ResponseObject(adActivityService.getOneByIdActivity(id));
    }

    @GetMapping("/activity-status/{status}")
    public ResponseObject getAllIdByStatus(@PathVariable("status") String status){
        return new ResponseObject(adActivityService.getAllIdByStatus(status));
    }

    @GetMapping("/activity-semester")
    public ResponseObject getSemester(){
        return new ResponseObject(adActivityService.getSemester());
    }
}
