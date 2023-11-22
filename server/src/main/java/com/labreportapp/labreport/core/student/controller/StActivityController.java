package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.service.StActivityService;
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
@RequestMapping("/api/student/activity")
public class StActivityController {

    @Autowired
    private StActivityService stActivityService;

    @GetMapping
    public ResponseObject getAllActivity(@RequestParam("semesterId") String semesterId) {
        return new ResponseObject(stActivityService.getAllActivity(semesterId));
    }
}
