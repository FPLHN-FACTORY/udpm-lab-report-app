package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.admin.service.AdTeacherService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894 - duchieu212
 */
@RestController
@RequestMapping("/api/teacher")
public class TeTeacherController {

    @Autowired
    private AdTeacherService adTeacherService;

    @GetMapping("/get-all-teacher")
    public ResponseObject getAllTeacher() {
        return new ResponseObject(adTeacherService.getAllTeacher());
    }

}
