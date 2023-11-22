package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.response.TeSemesterResponse;
import com.labreportapp.labreport.core.teacher.service.TeSemesterService;
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
@RequestMapping("/api/teacher/semester")
public class TeSemesterController {

    @Autowired
    private TeSemesterService teSemesterService;

    @GetMapping()
    public ResponseObject getAllSemester() {
        List<TeSemesterResponse> listSemester = teSemesterService.getAllSemester();
        return new ResponseObject(listSemester);
    }
}
