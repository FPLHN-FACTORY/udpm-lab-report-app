package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.labreport.core.student.model.request.StUpdateHomeWorkAndNotebyLeadTeamRequest;
import com.labreportapp.labreport.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.labreport.core.student.model.response.StMeetingResponse;
import com.labreportapp.labreport.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.labreport.core.student.service.StMeetingService;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/student/meeting")
@CrossOrigin("*")
public class StMeetingController {

    @Autowired
    private StMeetingService service;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @GetMapping("")
    public ResponseObject getStMeeting(final StFindMeetingRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        List<StMeetingResponse> list = service.searchMeetingByIdClass(request);
        return new ResponseObject(list);
    }

    @GetMapping("/count")
    public ResponseObject getCountStMeeting(final StFindMeetingRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        Integer count = service.countMeetingByClassId(request.getIdClass());
        return new ResponseObject(count);
    }

    @GetMapping("/detail")
    public ResponseObject getStMeetingDetail(final StFindMeetingRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        StMeetingResponse find = service.searchMeetingByIdMeeting(request);
        return new ResponseObject(find);
    }

    @GetMapping("/homework-note-report")
    public ResponseObject getStHomeWNoteMeetingDetail(final StFindMeetingRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        StHomeWordAndNoteResponse find = service.searchDetailMeetingTeamById(request);
        return new ResponseObject(find);
    }

    @PutMapping("/homework-note-report")
    public ResponseObject updateStHomeWNoteMeetingDetail(@RequestBody StUpdateHomeWorkAndNotebyLeadTeamRequest request) {
        StHomeWordAndNoteResponse find = service.updateDetailMeetingTeamByLeadTeam(request);
        return new ResponseObject(find);
    }

    @GetMapping("/get-team-meeting")
    public ResponseObject getStTeamsClass(final StFindMeetingRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        List<StMyTeamInClassResponse> pageList = service.getAllTeams(request);
        return new ResponseObject(pageList);
    }

    @GetMapping("/get-role")
    public ResponseObject getRoleByIdStudent(final StFindMeetingRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        Integer role = service.getRoleByIdStudent(request);
        return new ResponseObject(role);
    }

}
