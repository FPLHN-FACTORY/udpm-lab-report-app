package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.service.StTeamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/student/my-class/team")
@CrossOrigin("*")
public class StTeamClassController {

    @Autowired
    private StTeamClassService service;

    @GetMapping
    public ResponseObject getTeamClassByIdSt(final FindTeamClassRequest request) {
        return new ResponseObject(service.getTeamInClassByIdStudent(request));
    }
    @GetMapping("/my-person")
    public ResponseObject getMyTeam(final FindTeamClassRequest request) {
        return new ResponseObject(service.getMyStudentTeam(request));
    }
    @GetMapping("/my-person-not-join-team")
    public ResponseObject getTeamWithStNotJoin(final FindTeamClassRequest request) {
        return new ResponseObject(service.getTeamStNotJoin(request));
    }
}
