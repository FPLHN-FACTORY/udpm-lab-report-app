package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceMessageResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentAllResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentAttendedDetailRespone;
import com.labreportapp.labreport.core.teacher.service.TeAttendanceSevice;
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
        List<TeAttendanceResponse> list = teAttendanceSevice.getListCustom(idMeeting);
        return new ResponseObject(list);
    }

    @PostMapping("")
    public ResponseObject createOrUpdate(@RequestBody TeFindListAttendanceRequest request) {
        TeAttendanceMessageResponse list = teAttendanceSevice.addOrUpdateAttendance(request);
        return new ResponseObject(list);
    }

    @GetMapping("/attendance-all/{idClass}")
    public ResponseObject getAllAttendanceByIdClass(@PathVariable("idClass") String idClass) {
        List<TeAttendanceStudentAllResponse> listMeger = teAttendanceSevice.getListAttendanceStudentAllMeeting(idClass);
        return new ResponseObject(listMeger);
    }

    @GetMapping("/attendance-one-st")
    public ResponseObject getAllStudentAttendanceById(final TeFindStudentAttendanceRequest req) {
        PageableObject<TeStudentAttendedDetailRespone> pageList = teAttendanceSevice.getAllAttendanceStudentById(req);
        return new ResponseObject(pageList);
    }

}


