package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateListAttendanceRequest;
import com.labreportapp.labreport.core.admin.service.AdAttendanceService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/admin/attendance")
@CrossOrigin("*")
public class AdAttendanceController {

    @Autowired
    private AdAttendanceService adAttendanceService;

    @GetMapping
    public ResponseObject getAttendanceByIdMeeting(@RequestParam("idMeeting") String idMeeting, @RequestParam("idClass") String idClass) {
        return new ResponseObject(adAttendanceService.getAttendanceByIdMeeting(idMeeting, idClass));
    }

    @PostMapping
    public ResponseObject updateAttendance(@RequestBody AdUpdateListAttendanceRequest request) {
        return new ResponseObject(adAttendanceService.updateAttendance(request));
    }
}
