package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.labreport.core.student.model.response.StClassAttendenceAllCustomResponse;
import com.labreportapp.labreport.core.student.service.StAttendenceAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/attendence")
public class StAttendenceAllController {

  @Autowired
  private StAttendenceAllService stAttendenceAllService;

  @GetMapping("")
  public ResponseObject getClassByStudent(final StFindAttendenceAllRequest req) {
    List<StClassAttendenceAllCustomResponse> attendencesResponse = stAttendenceAllService.getClassAttendenceListByStudentInClassAndSemester(req);
    return new ResponseObject(attendencesResponse);
  }

}
