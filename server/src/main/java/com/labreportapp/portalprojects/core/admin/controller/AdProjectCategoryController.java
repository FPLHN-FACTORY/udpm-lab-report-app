package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectCategoryRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectCategoryRequest;
import com.labreportapp.portalprojects.core.admin.service.AdProjectCategoryService;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author NguyenVinh
 */
@RestController
@RequestMapping("/admin/project-category")
public class AdProjectCategoryController {

    @Autowired
    private AdProjectCategoryService adProjectCategoryService;

    @PostMapping()
    public ResponseObject addList(@RequestBody List<AdCreateProjectCategoryRequest> list) {
        return new ResponseObject(adProjectCategoryService.addListProjectCategory(list));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable("id") String id, @RequestBody AdUpdateProjectCategoryRequest request) {
        request.setId(id);
        return new ResponseObject(adProjectCategoryService.upadteProjectCategory(request));
    }

    @GetMapping("/list-category-project/{id}")
    public ResponseObject getAllByIdProject (@PathVariable ("id") String idProject){
        return new ResponseObject(adProjectCategoryService.getAllByidProject(idProject));
    }

    @PutMapping("/update/{id}")
    public ResponseObject delete (@PathVariable ("id") String idProject,@RequestBody List<AdUpdateProjectCategoryRequest> list){
        return new ResponseObject(adProjectCategoryService.updateListProjectCategory(idProject,list));
    }

}
