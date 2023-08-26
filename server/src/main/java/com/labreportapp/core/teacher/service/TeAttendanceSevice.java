package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
public interface TeAttendanceSevice {

    List<TeAttendanceRespone> getListAttendace(final TeFindListAttendanceRequest request);

}
