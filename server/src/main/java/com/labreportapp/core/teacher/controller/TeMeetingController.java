package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.core.teacher.service.TeMeetingService;
import com.labreportapp.core.teacher.service.TeStudentClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/meeting")
@CrossOrigin(origins = {"*"})
public class TeMeetingController {

    @Autowired
    private TeMeetingService teMeetingService;

    @GetMapping("")
    public ResponseObject getTeMeeting(final TeFindMeetingRequest request) {
        List<TeMeetingRespone> list = teMeetingService.searchMeetingByIdClass(request);
        return new ResponseObject(list);
    }
}
