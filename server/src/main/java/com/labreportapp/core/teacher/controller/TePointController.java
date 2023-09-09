package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.response.TePointRespone;
import com.labreportapp.core.teacher.service.TePointSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/point")
@CrossOrigin(origins = {"*"})
public class TePointController {

    @Autowired
    private TePointSevice tePointSevice;

    @GetMapping("/get/{idClass}")
    public ResponseObject getPointByIdClass(@PathVariable("idClass") String idClass) {
        List<TePointRespone> list = tePointSevice.getPointStudentById(idClass);
        return new ResponseObject(list);
    }

}


