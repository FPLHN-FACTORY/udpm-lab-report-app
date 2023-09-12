package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.core.student.model.response.StAttendanceRespone;

import java.util.List;

public interface StAttendanceService {
    List<StAttendanceRespone> getAllAttendanceById(final StFindAttendanceRequest req);
}