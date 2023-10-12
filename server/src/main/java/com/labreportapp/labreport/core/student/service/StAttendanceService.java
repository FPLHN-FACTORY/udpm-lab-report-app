package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendanceCallApiRespone;
import com.labreportapp.labreport.core.student.model.response.StAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentAttendedDetailRespone;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StAttendanceService {

    List<StAttendanceRespone> getAllAttendanceById(final StFindAttendanceRequest req);

    PageableObject<StAttendanceCallApiRespone> getAllAttendanceStudentById(final StFindAttendanceRequest request);
}
