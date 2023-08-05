package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.response.TeSemesterRespone;
import com.labreportapp.core.teacher.service.TeSemesterService;
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
@RequestMapping("/teacher/semester")
@CrossOrigin(origins = {"*"})
public class TeSemesterController {

    @Autowired
    private TeSemesterService teSemesterService;

    @GetMapping()
    public ResponseObject getAllSemester() {
        List<TeSemesterRespone> listSemester = teSemesterService.getAllSemester();
        return new ResponseObject(listSemester);
    }

}
