package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.StFindPointAllRequest;
import com.labreportapp.core.student.model.response.StPointAllCustomRespone;

import java.util.List;

public interface StPointAllService {

    List<StPointAllCustomRespone> getClassPointListByStudentInClassAndSemester(final StFindPointAllRequest req);
}
