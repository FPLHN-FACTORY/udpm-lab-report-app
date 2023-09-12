package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.entity.Attendance;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeAttendanceSevice {

    List<TeAttendanceRespone> getListCustom(String idMeeting);

    List<Attendance> addOrUpdateAttendance(final TeFindListAttendanceRequest request);

}
