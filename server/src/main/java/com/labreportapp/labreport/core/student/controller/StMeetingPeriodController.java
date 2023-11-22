package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.service.StMeetingPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/api/student/meeting-period")
public class StMeetingPeriodController {

    @Autowired
    private StMeetingPeriodService stMeetingPeriodService;

    @GetMapping("")
    public ResponseObject showMeetingPeriod() {
        return new ResponseObject(stMeetingPeriodService.listMeetingPeriod());
    }
}
