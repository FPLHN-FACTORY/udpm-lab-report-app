package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.core.teacher.model.response.TeClassResponse;
import com.labreportapp.core.teacher.service.TeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/class")
@CrossOrigin(origins = {"*"})
public class TeClassController {

    @Autowired
    private TeClassService teClassService;

    @GetMapping("")
    public ResponseObject searchTeClass(final TeFindClassRequest teFindClass) {
        PageableObject<TeClassResponse> pageList = teClassService.searchTeacherClass(teFindClass);
        return new ResponseObject(pageList);
    }

    @GetMapping("/{id}")
    public ResponseObject detailClass(@PathVariable("id") String id) {
        return new ResponseObject(teClassService.findClassById(id));
    }

    @GetMapping("/semester-nearest/{id}")
    public ResponseObject getClassClosestToTheDateToSemester(@PathVariable("id") String id) {
        return new ResponseObject(teClassService.getClassClosestToTheDateToSemester(id));
    }

}
