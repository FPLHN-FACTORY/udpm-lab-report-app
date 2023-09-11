package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StAttendenceAllCustomResponse;
import com.labreportapp.core.student.model.response.StAttendenceAllResponse;
import com.labreportapp.core.student.model.response.StMyClassResponse;
import com.labreportapp.core.student.repository.StAttendenceAllRepository;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.service.StAttendenceAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StAttendenceAllServiceImpl implements StAttendenceAllService {

  @Autowired
  private StMyClassRepository stMyClassRepository;

  @Autowired
  private StAttendenceAllRepository stAttendenceAllRepository;

  @Override
  public List<StAttendenceAllCustomResponse> getClassAttendenceListByStudentInClassAndSemester(StFindAttendenceAllRequest req) {
    StFindClassRequest stFindClassRequest = new StFindClassRequest();
    stFindClassRequest.setSemesterId(req.getIdSemester());
    stFindClassRequest.setStudentId(req.getIdStudent());
    List<StMyClassResponse> getClassListByStudentInSemester = stMyClassRepository.getAllClass(stFindClassRequest);

    List<StAttendenceAllCustomResponse> attendencesResponse = new ArrayList<>();

    for (StMyClassResponse classResponse : getClassListByStudentInSemester) {
      StAttendenceAllCustomResponse attendenceResponse = new StAttendenceAllCustomResponse();
      attendenceResponse.setId(classResponse.getId());
      attendenceResponse.setClassCode(classResponse.getCode());

      req.setIdClass(attendenceResponse.getId());
      List<StAttendenceAllResponse> getAttendenceListByStudentInClassAndSemester = stAttendenceAllRepository.getAttendenceListByStudentInClassAndSemester(req);
      attendenceResponse.setAttendences(getAttendenceListByStudentInClassAndSemester);

      attendencesResponse.add(attendenceResponse);
    }

    return attendencesResponse;
  }
}
