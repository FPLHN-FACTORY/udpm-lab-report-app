package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.service.TeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894 - duchieu212
 */
@RestController
@RequestMapping("/teacher/category")
public class TeCategoryController {

    @Autowired
    private TeCategoryService teCategoryService;

    @GetMapping("")
    public ResponseObject listCategory() {
        return new ResponseObject(teCategoryService.getAllCategory());
    }
}
