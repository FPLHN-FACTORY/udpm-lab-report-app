package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendanceCallApiRespone;
import com.labreportapp.labreport.core.student.model.response.StAttendanceRespone;

import java.util.List;

public interface StAttendanceService {
    List<StAttendanceRespone> getAllAttendanceById(final StFindAttendanceRequest req);

    List<StAttendanceCallApiRespone> getAllAttendanceStudentById(final StFindAttendanceRequest request);
}
