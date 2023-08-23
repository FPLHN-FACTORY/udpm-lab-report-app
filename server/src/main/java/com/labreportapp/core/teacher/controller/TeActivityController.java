package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.core.teacher.model.response.TeActivityRespone;
import com.labreportapp.core.teacher.service.TeActivitySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/activity")
@CrossOrigin(origins = {"*"})
public class TeActivityController {

    @Autowired
    private TeActivitySevice teActivitySevice;

    @GetMapping("/id-semester")
    public ResponseObject listActivitySemester(final TeFindClassRequest teFindClass) {
        List<TeActivityRespone> list = teActivitySevice.getAllByIdSemester(teFindClass);
        return new ResponseObject(list);
    }

}