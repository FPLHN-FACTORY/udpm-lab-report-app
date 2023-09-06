package com.labreportapp.core.admin.controller;

import com.labreportapp.core.admin.service.AdTeacherService;
import com.labreportapp.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/admin/teacher")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdTeacherController {

    @Autowired
    private AdTeacherService adTeacherService;

    @GetMapping
    public ResponseObject getAllTeacher() {
        return new ResponseObject(adTeacherService.getAllTeacher());
    }
}
