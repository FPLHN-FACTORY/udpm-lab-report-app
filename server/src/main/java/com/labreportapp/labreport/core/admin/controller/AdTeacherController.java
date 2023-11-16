package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.service.AdTeacherService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/admin/teacher")
public class AdTeacherController {

    @Autowired
    private AdTeacherService adTeacherService;

    @GetMapping
    public ResponseObject getAllTeacher() {
        return new ResponseObject(adTeacherService.getAllTeacher());
    }

    @GetMapping("/dashboard")
    public ResponseObject getAllTeacherDashBoard(@RequestParam("name") String name) {
        return new ResponseObject(adTeacherService.getAllTeacherDashBoard(name));
    }
}
