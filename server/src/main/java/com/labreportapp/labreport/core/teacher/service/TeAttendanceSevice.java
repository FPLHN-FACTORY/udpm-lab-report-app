package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentAllRespone;
import com.labreportapp.labreport.entity.Attendance;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeAttendanceSevice {

    List<TeAttendanceRespone> getListCustom(String idMeeting);

    List<Attendance> addOrUpdateAttendance(final TeFindListAttendanceRequest request);

    List<TeAttendanceStudentAllRespone> getListAttendanceStudentAllMeeting(String idClass);
    
}
