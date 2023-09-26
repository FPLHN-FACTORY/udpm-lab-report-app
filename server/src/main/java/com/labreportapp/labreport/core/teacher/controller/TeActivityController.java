package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeActivityResponse;
import com.labreportapp.labreport.core.teacher.service.TeActivitySevice;
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
@RequestMapping("/teacher/activity")
@CrossOrigin(origins = {"*"})
public class TeActivityController {

    @Autowired
    private TeActivitySevice teActivitySevice;

    @GetMapping("/id-semester")
    public ResponseObject listActivitySemester(final TeFindClassRequest teFindClass) {
        List<TeActivityResponse> list = teActivitySevice.getAllByIdSemester(teFindClass);
        return new ResponseObject(list);
    }

}
