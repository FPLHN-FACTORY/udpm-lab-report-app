package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.labreport.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.labreport.core.student.model.request.StChangeLeaderRequest;
import com.labreportapp.labreport.core.student.model.request.StJoinTeamRequest;
import com.labreportapp.labreport.core.student.model.request.StOutTeamRequest;
import com.labreportapp.labreport.core.student.service.StTeamClassService;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/api/student/my-class/team")
public class StTeamClassController {

    @Autowired
    private StTeamClassService service;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @GetMapping("/get-team-in-class")
    public ResponseObject getTeamInClass(final FindTeamByIdClass request) {
        return new ResponseObject(service.getTeamInClass(request));
    }

    @GetMapping("/detail-team")
    public ResponseObject detailTeam(@RequestParam("idTeam") String idTeam) {
        return new ResponseObject(service.detailTeam(idTeam));
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
    public ResponseObject checkStatusStudentInClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(service.checkStatusStudentInClass(idClass, labReportAppSession.getUserId()));
    }

    @GetMapping("/detail")
    public ResponseObject detailClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(service.detailClass(idClass));
    }

    @PutMapping("/join-team")
    public ResponseObject joinTeam(@RequestBody StJoinTeamRequest request) {
        return new ResponseObject(service.joinTeam(request));
    }

    @PutMapping("/out-team")
    public ResponseObject outTeam(@RequestBody StOutTeamRequest request) {
        return new ResponseObject(service.outTeam(request));
    }

    @PutMapping("/change-leader")
    public ResponseObject changeLeader(@RequestBody StChangeLeaderRequest request) {
        return new ResponseObject(service.changeLeader(request));
    }

    @GetMapping("/test")
    public ResponseObject test() {
        return new ResponseObject(service.test());
    }
}
