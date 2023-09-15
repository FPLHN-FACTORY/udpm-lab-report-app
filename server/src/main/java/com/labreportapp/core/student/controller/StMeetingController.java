package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.core.student.model.request.StUpdateHomeWorkAndNotebyLeadTeamRequest;
import com.labreportapp.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.core.student.model.response.StMeetingResponse;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.core.student.service.StMeetingService;
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

    @GetMapping("")
    public ResponseObject getStMeeting(final StFindMeetingRequest request) {
        List<StMeetingResponse> list = service.searchMeetingByIdClass(request);
        return new ResponseObject(list);
    }

    @GetMapping("/count")
    public ResponseObject getCountTeMeeting(final StFindMeetingRequest request) {
        Integer count = service.countMeetingByClassId(request.getIdClass());
        return new ResponseObject(count);
    }

    @GetMapping("/detail")
    public ResponseObject getTeMeetingDetail(final StFindMeetingRequest request) {
        StMeetingResponse find = service.searchMeetingByIdMeeting(request);
        return new ResponseObject(find);
    }

    @GetMapping("/homeword-and-note")
    public ResponseObject getTeHomeWNoteMeetingDetail(final StFindMeetingRequest request) {
        StHomeWordAndNoteResponse find = service.searchDetailMeetingTeamById(request);
        return new ResponseObject(find);
    }

    @PutMapping("/homeword-and-note")
    public ResponseObject updateTeHomeWNoteMeetingDetail(@RequestBody StUpdateHomeWorkAndNotebyLeadTeamRequest request) {
        StHomeWordAndNoteResponse find = service.updateDetailMeetingTeamByLeadTeam(request);
        return new ResponseObject(find);
    }

    @GetMapping("/get-team-meeting")
    public ResponseObject getTeTeamsClass(final StFindMeetingRequest request) {
        List<StMyTeamInClassResponse> pageList = service.getAllTeams(request);
        return new ResponseObject(pageList);
    }

    @GetMapping("/get-role")
    public ResponseObject getRoleByIdStudent(final StFindMeetingRequest request) {
        Integer role = service.getRoleByIdStudent(request);
        return new ResponseObject(role);
    }
}
