package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailMeetingTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassResponse;
import com.labreportapp.labreport.core.teacher.service.TeMeetingService;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/api/teacher/meeting")
public class TeMeetingController {

    @Autowired
    private TeMeetingService teMeetingService;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @GetMapping("")
    public ResponseObject getTeMeeting(final TeFindMeetingRequest request) {
        List<TeMeetingCustomResponse> list = teMeetingService.searchMeetingByIdClass(request);
        return new ResponseObject(list);
    }

    @GetMapping("/detail")
    public ResponseObject getTeMeetingDetail(final TeFindMeetingRequest request) {
        TeDetailMeetingTeamReportRespone obj = teMeetingService.searchMeetingByIdMeeting(request);
        return new ResponseObject(obj);
    }

    @GetMapping("/hw-note")
    public ResponseObject getTeHomeWNoteMeetingDetail(final TeFindMeetingRequest request) {
        TeHomeWorkAndNoteMeetingResponse find = teMeetingService.searchDetailMeetingTeamByIdMeIdTeam(request);
        return new ResponseObject(find);
    }

    @PutMapping("/hw-note-report")
    public ResponseObject updateTeHomeWNoteMeetingDetail(@RequestBody TeUpdateHomeWorkAndNoteInMeetingRequest request) {
        TeHomeWorkAndNoteMeetingResponse find = teMeetingService.updateDetailMeetingTeamByIdMeIdTeam(request);
        return new ResponseObject(find);
    }

    @GetMapping("/detail-attendance")
    public ResponseObject getTeMeetingAndCheckAttendance(final TeFindMeetingRequest request) {
        TeMeetingResponse find = teMeetingService.searchMeetingAndCheckAttendanceByIdMeeting(request);
        return new ResponseObject(find);
    }

    @GetMapping("/schedule-today")
    public ResponseObject getScheduleTodayTeacher() {
        List<TeScheduleMeetingClassResponse> list = teMeetingService.searchScheduleToDayByIdTeacherAndMeetingDate();
        return new ResponseObject(list);
    }

    @GetMapping("/schedule-time")
    public ResponseObject getScheduleTodayTeacherTime(final TeFindScheduleNowToTime request) {
        request.setIdTeacher(labReportAppSession.getUserId());
        PageableObject<TeScheduleMeetingClassResponse> list = teMeetingService.searchScheduleNowToByIdTeacher(request);
        return new ResponseObject(list);
    }

    @PutMapping("/schedule")
    public ResponseObject updateScheduleTodayTeacher(@RequestBody TeScheduleUpdateMeetingRequest request) {
        List<TeScheduleMeetingClassResponse> list = teMeetingService.updateAddressMeeting(request);
        return new ResponseObject(list);
    }

    @GetMapping("/column-attendance/{idClass}")
    public ResponseObject getColumnMeetingByIdClass(@PathVariable("idClass") String idClass) {
        List<TeMeetingCustomToAttendanceResponse> list = teMeetingService.listMeetingAttendanceAllByIdClass(idClass);
        return new ResponseObject(list);
    }

}
