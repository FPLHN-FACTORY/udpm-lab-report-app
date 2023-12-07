package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdReasonsNoApproveRequest;
import com.labreportapp.labreport.core.admin.repository.AdSemesterRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingRequestService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.SemesterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @Autowired
    private AdSemesterRepository semesterRepository;

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

    @PutMapping("/add-reasons")
    public ResponseObject addReason(@RequestParam("idClass") String idClass, @RequestParam("reasons") String reasons) {
        return new ResponseObject(adMeetingRequestService.addReason(idClass, reasons));
    }

    @PutMapping("/add-reasons-class")
    public ResponseObject addReasonClass(@RequestBody AdReasonsNoApproveRequest request) {
        return new ResponseObject(adMeetingRequestService.addReasonClass(request));
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv(@RequestParam(name = "idSemester", defaultValue = "") String idSemester) {
        String nameSemester = semesterRepository.findById(idSemester).get().getName();
        String pathFile = loggerUtil.getPathFileSendLogScreen(nameSemester);
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "50") Integer size,
                                         @RequestParam(name = "idSemester", defaultValue = "") String idSemester
    ) {
        String nameSemester = semesterRepository.findById(idSemester).get().getName();
        String pathFile = loggerUtil.getPathFileSendLogScreen(nameSemester);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }
}
