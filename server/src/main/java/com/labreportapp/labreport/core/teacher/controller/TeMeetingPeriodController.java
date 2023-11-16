package com.labreportapp.labreport.core.teacher.controller;

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
@RequestMapping("/teacher/meeting-period")
public class TeMeetingPeriodController {

    @Autowired
    private TeMeetingPeriodService teMeetingPeriodService;

    @GetMapping("")
    public ResponseObject showMeetingPeriod() {
        return new ResponseObject(teMeetingPeriodService.listMeetingPeriod());
    }
}
