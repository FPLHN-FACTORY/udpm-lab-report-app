package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeFindClass;
import com.labreportapp.core.teacher.model.response.TeClassResponse;
import com.labreportapp.core.teacher.service.TeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = {"*"})
public class TeClassController {

    @Autowired
    private TeClassService teClassService;

    @GetMapping("")
    public ResponseObject searchTeClass(final TeFindClass teFindClass) {
        PageableObject<TeClassResponse> pageList = teClassService.searchTeacherClass(teFindClass);
        return new ResponseObject(pageList);
    }
}
