package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.labreport.core.student.model.response.StClassAttendenceAllCustomResponse;

import java.util.List;

public interface StAttendenceAllService {

  List<StClassAttendenceAllCustomResponse> getClassAttendenceListByStudentInClassAndSemester(final StFindAttendenceAllRequest req);

}
