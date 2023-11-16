package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdRoleFactoryResponse;
import com.labreportapp.labreport.core.admin.service.AdRoleFactoryService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.RoleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/role-factory")
public class AdRoleFactoryController {
    @Autowired
    private AdRoleFactoryService adRoleFactoryService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllRoleFactory(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<RoleFactory> RoleFactoryList = adRoleFactoryService.findAllRoleFactory(pageResquest);
        return ResponseEntity.ok(RoleFactoryList);
    }

    @GetMapping("")
    public ResponseObject viewRoleFactory(@ModelAttribute final AdFindRoleFactoryRequest request) {
        return new ResponseObject((adRoleFactoryService.searchRoleFactory(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchRoleFactory(final AdFindRoleFactoryRequest request) {
        PageableObject<AdRoleFactoryResponse> listRoleFactory = adRoleFactoryService.searchRoleFactory(request);
        return new ResponseObject(listRoleFactory);
    }

    @PostMapping("/add")
    public ResponseObject addRoleFactory(@RequestBody AdCreateRoleFactoryRequest obj) {
        return new ResponseObject(adRoleFactoryService.createRoleFactory(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteRoleFactory(@PathVariable("id") String id) {
        return new ResponseObject(adRoleFactoryService.deleteRoleFactory(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateRoleFactory(@PathVariable("id") String id,
                                     @RequestBody AdUpdateRoleFactoryRequest obj) {
        obj.setId(id);
        return new ResponseObject(adRoleFactoryService.updateRoleFactory(obj));
    }
}
