package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.service.StSemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/student/semester")
public class StSemesterController {

    @Autowired
    private StSemesterService stSemesterService;

    @GetMapping
    public ResponseObject getAllSemester() {
        return new ResponseObject(stSemesterService.getAllSemester());
    }

}
