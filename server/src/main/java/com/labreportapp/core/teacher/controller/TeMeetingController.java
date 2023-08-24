package com.labreportapp.core.teacher.controller;

import com.labreportapp.core.common.base.ResponseObject;
import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.service.TeMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/count")
    public ResponseObject getCountTeMeeting(final TeFindMeetingRequest request) {
        Integer count = teMeetingService.countMeetingByClassId(request.getIdClass());
        return new ResponseObject(count);
    }

    @GetMapping("/detail")
    public ResponseObject getTeMeetingDetail(final TeFindMeetingRequest request) {
        TeMeetingRespone find = teMeetingService.searchMeetingByIdMeeting(request);
        return new ResponseObject(find);
    }

    @GetMapping("/hw-note")
    public ResponseObject getTeHomeWNoteMeetingDetail(final TeFindMeetingRequest request) {
        TeHomeWorkAndNoteMeetingRespone find = teMeetingService.searchDetailMeetingTeamByIdMeIdTeam(request);
        return new ResponseObject(find);
    }

    @PutMapping("/hw-note")
    public ResponseObject updateTeHomeWNoteMeetingDetail(@RequestBody TeUpdateHomeWorkAndNoteInMeetingRequest request) {
        TeHomeWorkAndNoteMeetingRespone find = teMeetingService.updateDetailMeetingTeamByIdMeIdTeam(request);
        return new ResponseObject(find);
    }

//    @GetMapping("/details")
//    public ResponseObject getTeamMeetingHomeNoteDetail(final TeFindMeetingRequest request) {
//        List<TeHomeWorkAndNoteMeetingRespone> find = teMeetingService.searchMeetingHomeWNoteByIdMeetingAndIdClass(request);
//        return new ResponseObject(find);
//    }
}
