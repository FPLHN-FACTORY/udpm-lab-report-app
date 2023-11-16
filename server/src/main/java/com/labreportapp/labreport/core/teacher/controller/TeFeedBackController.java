package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.service.TeFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894 - duchieu212
 */
@RestController
@RequestMapping("/teacher/feedback")
public class TeFeedBackController {

    @Autowired
    private TeFeedBackService teFeedBackService;

    @GetMapping("/filter-class")
    public ResponseObject getAllFeedbackDetailIdClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(teFeedBackService.getAllFeedbackByIdClass(idClass));
    }
}
