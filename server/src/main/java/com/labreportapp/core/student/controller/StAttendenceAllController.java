package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.core.student.model.response.StAttendenceAllCustomResponse;
import com.labreportapp.core.student.service.StAttendenceAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student/attendence")
@CrossOrigin("*")
public class StAttendenceAllController {

  @Autowired
  private StAttendenceAllService stAttendenceAllService;

  @GetMapping("")
  public ResponseObject getClassByStudent(final StFindAttendenceAllRequest req) {
    List<StAttendenceAllCustomResponse> attendencesResponse = stAttendenceAllService.getClassAttendenceListByStudentInClassAndSemester(req);
    return new ResponseObject(attendencesResponse);
  }

}
