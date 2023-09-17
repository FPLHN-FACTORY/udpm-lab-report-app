package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StFindPointAllRequest;
import com.labreportapp.labreport.core.student.model.response.StPointAllCustomRespone;
import com.labreportapp.labreport.core.student.service.StPointAllService;
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
