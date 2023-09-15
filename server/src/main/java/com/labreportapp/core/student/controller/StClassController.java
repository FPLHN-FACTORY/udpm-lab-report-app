package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.request.StClassRequest;
import com.labreportapp.core.student.model.response.StClassCustomResponse;
import com.labreportapp.core.student.service.StClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student/class")
@CrossOrigin("*")
public class StClassController {

  @Autowired
  private StClassService stClassService;

  @GetMapping("")
  public ResponseObject getClassByCriteriaAndIsActive(final StFindClassRequest req) {
    List<StClassCustomResponse> response = stClassService.getAllClassByCriteriaAndIsActive(req);
    return new ResponseObject(response);
  }

  @PostMapping("/join")
  public ResponseObject studentJoinClass(final StClassRequest req) {
    StClassCustomResponse response = stClassService.joinClass(req);
    return new ResponseObject(response);
  }

}
