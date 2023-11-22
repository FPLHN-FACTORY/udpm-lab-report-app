package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.service.AdMeetingPeriodService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/admin/meeting-period")
public class AdMeetingPeriodController {

    @Autowired
    private AdMeetingPeriodService adMeetingPeriodService;

    @GetMapping
    public ResponseObject getAllMeetingPeriod() {
        return new ResponseObject(adMeetingPeriodService.getAllMeetingPeriod());
    }
}
