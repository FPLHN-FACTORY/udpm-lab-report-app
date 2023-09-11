package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.core.student.model.response.StAttendenceAllCustomResponse;

import java.util.List;

public interface StAttendenceAllService {

  List<StAttendenceAllCustomResponse> getClassAttendenceListByStudentInClassAndSemester(final StFindAttendenceAllRequest req);

}
