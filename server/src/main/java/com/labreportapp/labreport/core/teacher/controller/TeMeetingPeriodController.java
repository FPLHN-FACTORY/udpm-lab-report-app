package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.admin.service.AdMeetingPeriodService;
import com.labreportapp.labreport.core.admin.service.AdTeacherService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.service.TeMeetingPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/api/teacher/meeting-period")
public class TeMeetingPeriodController {

    @Autowired
    private TeMeetingPeriodService teMeetingPeriodService;

    @Autowired
    private AdMeetingPeriodService adMeetingPeriodService;

    @Autowired
    private AdTeacherService adTeacherService;

    @GetMapping("")
    public ResponseObject showMeetingPeriod() {
        return new ResponseObject(teMeetingPeriodService.listMeetingPeriod());
    }

    @GetMapping("/get-all")
    public ResponseObject getAllMeetingPeriod() {
        return new ResponseObject(adMeetingPeriodService.getAllMeetingPeriod());
    }

    @GetMapping("/teacher")
    public ResponseObject getAllTeacher() {
        return new ResponseObject(adTeacherService.getAllTeacher());
    }
}
