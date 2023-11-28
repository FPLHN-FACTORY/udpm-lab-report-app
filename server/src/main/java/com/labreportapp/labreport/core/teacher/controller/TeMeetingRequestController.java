package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeMeetingRequestAgainRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRequestCustomResponse;
import com.labreportapp.labreport.core.teacher.service.TeMeetingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/api/teacher/meeting-request")
public class TeMeetingRequestController {

    @Autowired
    private TeMeetingRequestService teMeetingRequestService;

    @GetMapping("")
    public ResponseObject showTableMeetingRequest(final TeFindMeetingRequestRequest request) {
        Page<TeMeetingRequestCustomResponse> list = teMeetingRequestService.getAll(request);
        return new ResponseObject(list);
    }

    @PutMapping("sent-again")
    public ResponseObject sentMeetingRequestAgain(@RequestBody TeMeetingRequestAgainRequest request) {
        return new ResponseObject(teMeetingRequestService.sendMeetingRequestAgain(request));
    }
}