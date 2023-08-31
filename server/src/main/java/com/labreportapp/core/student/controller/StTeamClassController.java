package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.service.StTeamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/get-team-in-class")
    public ResponseObject getTeamInClass(final FindTeamByIdClass request) {
        return new ResponseObject(service.getTeamInClass(request));
    }

    @GetMapping("/call-api")
    public ResponseObject callApi() {
        return new ResponseObject(service.callApi());
    }

    @GetMapping("/get-student-in-my-team")
    public ResponseObject getStudentInMyTeam(final FindTeamClassRequest request) {
        return new ResponseObject(service.getStudentInMyTeam(request));
    }

    @GetMapping("/check-status")
    public ResponseObject checkStatusStudentInClass(@RequestParam("idClass") String idClass, @RequestParam("idStudent") String idStudent) {
        return new ResponseObject(service.checkStatusStudentInClass(idClass, idStudent));
    }

    @GetMapping("/detail")
    public ResponseObject detailClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(service.detailClass(idClass));
    }

}
