package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.service.StActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/student/activity")
@CrossOrigin("*")
public class StActivityController {

    @Autowired
    private StActivityService stActivityService;

    @GetMapping
    public ResponseObject getAllActivity(@RequestParam("semesterId") String semesterId) {
        return new ResponseObject(stActivityService.getAllActivity(semesterId));
    }
}
