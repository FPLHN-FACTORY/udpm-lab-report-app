package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.service.TeAttendanceSevice;
import com.labreportapp.entity.Attendance;
import com.labreportapp.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/attendance")
@CrossOrigin(origins = {"*"})
public class TeAttendanceController {

    @Autowired
    private TeAttendanceSevice teAttendanceSevice;

    @GetMapping("/get/{idMeeting}")
    public ResponseObject getTeAttendanceStudentByIdMeeting(@PathVariable("idMeeting") String idMeeting) {
        List<TeAttendanceRespone> list = teAttendanceSevice.getListCustom(idMeeting);
        return new ResponseObject(list);
    }

    @PostMapping("")
    public ResponseObject createOrUpdate(@RequestBody TeFindListAttendanceRequest request) {
        List<Attendance> list = teAttendanceSevice.getListAttendace(request);
        return new ResponseObject(list);
    }

}


