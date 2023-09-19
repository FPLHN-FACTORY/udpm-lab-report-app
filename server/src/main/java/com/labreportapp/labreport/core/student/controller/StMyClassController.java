package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.service.StMyClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/leave")
    public ResponseObject leaveClass(final StClassRequest req) {
        stMyClassService.leaveClass(req);
        return new ResponseObject(null);
    }

    @GetMapping("/level")
    public ResponseObject getAllSimpleEntityProj() {
        return new ResponseObject(stMyClassService.getAllSimpleEntityProj());
    }
}