package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.service.AdMeetingRequestService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author todo thangncph26123
 */
@RestController
@RequestMapping("/api/admin/meeting-request")
public class AdMeetingRequestController {

    @Autowired
    private AdMeetingRequestService adMeetingRequestService;

    @GetMapping("/get-all-class-have-meeting-request")
    public ResponseObject getAllClassHaveMeetingRequest(final AdFindClassRequest adFindClass) {
        return new ResponseObject(adMeetingRequestService.searchClassHaveMeetingRequest(adFindClass));
    }

    @GetMapping("/count-class-have-meeting-request")
    public ResponseObject countClassHaveMeetingRequest(final AdFindClassRequest adFindClass) {
        return new ResponseObject(adMeetingRequestService.countClassHaveMeetingRequest(adFindClass));
    }

    @GetMapping("/get-all-meeting-request-by-id-class")
    public ResponseObject getAllMeetingRequestByIdClass(@RequestParam("id") String id) {
        return new ResponseObject(adMeetingRequestService.getAllMeetingRequestByIdClass(id));
    }

    @PostMapping("/approve-meeting-request")
    public ResponseObject approveMeetingRequest(@RequestBody List<String> listIdMeetingRequest) {
        return new ResponseObject(adMeetingRequestService.approveMeetingRequest(listIdMeetingRequest));
    }

    @PostMapping("/no-approve-meeting-request")
    public ResponseObject noApproveMeetingRequest(@RequestBody List<String> listIdMeetingRequest) {
        return new ResponseObject(adMeetingRequestService.noApproveMeetingRequest(listIdMeetingRequest));
    }

    @PostMapping("/approve-class")
    public ResponseObject approveClass(@RequestBody List<String> listIdClass) {
        return new ResponseObject(adMeetingRequestService.approveClass(listIdClass));
    }

    @PostMapping("/no-approve-class")
    public ResponseObject noApproveClass(@RequestBody List<String> listIdClass) {
        return new ResponseObject(adMeetingRequestService.noApproveClass(listIdClass));
    }
}
