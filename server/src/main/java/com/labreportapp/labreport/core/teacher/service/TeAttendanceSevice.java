package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceMessageResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentAllResponse;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeAttendanceSevice {

    List<TeAttendanceResponse> getListCustom(String idMeeting);

    TeAttendanceMessageResponse addOrUpdateAttendance(final TeFindListAttendanceRequest request);

    List<TeAttendanceStudentAllResponse> getListAttendanceStudentAllMeeting(String idClass);

}
