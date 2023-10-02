package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StFindScheduleRequest;
import com.labreportapp.labreport.core.student.model.response.StScheduleResponse;
import com.labreportapp.labreport.core.student.service.StScheduleService;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student/schedule")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class StScheduleController {

    @Autowired
    private StScheduleService stScheduleService;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllMeeting(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<Meeting> meetingList = stScheduleService.findAllMeeting(pageResquest);
        return ResponseEntity.ok(meetingList);
    }

    @GetMapping("")
    public ResponseObject viewMeeting(@ModelAttribute final StFindScheduleRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        return new ResponseObject((stScheduleService.findScheduleByStudent(request)));
    }

    @GetMapping("/getAll")
    public ResponseObject findScheduleByStudent(final StFindScheduleRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        PageableObject<StScheduleResponse> listSchedule = stScheduleService.findScheduleByStudent(request);
        return new ResponseObject(listSchedule);
    }
}
