package com.labreportapp.core.student.controller;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.student.model.request.StFindScheduleRequest;
import com.labreportapp.core.student.model.response.StScheduleResponse;
import com.labreportapp.core.student.service.StScheduleService;
import com.labreportapp.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/schedule")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class StScheduleController {

    @Autowired
    private StScheduleService stScheduleService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllMeeting(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<Meeting> meetingList = stScheduleService.findAllMeeting(pageResquest);
        return ResponseEntity.ok(meetingList);
    }

    @GetMapping("")
    public ResponseObject viewMeeting(@ModelAttribute final StFindScheduleRequest request) {
        return new ResponseObject((stScheduleService.findScheduleByStudent(request)));
    }

    @GetMapping("/getAll")
    public ResponseObject findScheduleByStudent(final StFindScheduleRequest request) {
        PageableObject<StScheduleResponse> listSchedule = stScheduleService.findScheduleByStudent(request);
        return new ResponseObject(listSchedule);
    }
}
