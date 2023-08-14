package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.core.teacher.service.TeStudentClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/student-classes")
@CrossOrigin(origins = {"*"})
public class TeStudentClassesController {

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @GetMapping("")
    public ResponseObject getTeStudentClasses(final TeFindStudentClasses request) {
        List<TeStudentClassesRespone> pageList = teStudentClassesService.searchStudentClassesByIdClass(request);
        return new ResponseObject(pageList);
    }

    @GetMapping("/team")
    public ResponseObject getTeStudentClassesByIdClassAndIdTeam(final TeFindStudentClasses request) {
        List<TeStudentClassesRespone> pageList = teStudentClassesService.searchStudentClassesByIdClassAndIdTeam(request);
        return new ResponseObject(pageList);
    }

}
