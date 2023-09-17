package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendenceAllCustomResponse;

import java.util.List;

public interface StAttendenceAllService {

  List<StAttendenceAllCustomResponse> getClassAttendenceListByStudentInClassAndSemester(final StFindAttendenceAllRequest req);

}
