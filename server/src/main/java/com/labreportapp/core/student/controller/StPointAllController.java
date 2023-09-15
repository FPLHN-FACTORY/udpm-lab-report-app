package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.StFindPointAllRequest;
import com.labreportapp.core.student.model.response.StPointAllCustomRespone;
import com.labreportapp.core.student.model.response.StPointAllRespone;
import com.labreportapp.core.student.service.StPointAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student/point")
@CrossOrigin("*")
public class StPointAllController {
    @Autowired
    private StPointAllService stPointAllService;

    @GetMapping("")
    public ResponseObject getPointByStudent(final StFindPointAllRequest req){
        List<StPointAllCustomRespone> getPointByStudent = stPointAllService.getClassPointListByStudentInClassAndSemester(req);
        return new ResponseObject(getPointByStudent);
    }
}
