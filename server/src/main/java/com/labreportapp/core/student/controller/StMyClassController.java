package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.service.StMyClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/student/my-class")
@CrossOrigin("*")
public class StMyClassController {

    @Autowired
    private StMyClassService stMyClassService;

    @GetMapping
    public ResponseObject getAllClass(final StFindClassRequest request) {
        System.out.println(request);
        return new ResponseObject(stMyClassService.getAllClass(request));
    }
}
