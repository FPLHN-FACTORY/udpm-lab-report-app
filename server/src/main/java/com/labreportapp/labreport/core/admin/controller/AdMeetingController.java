package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdChangeTeacherRequest;
import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingAutoRequest;
import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.labreport.core.admin.service.AdMeetingService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/admin/meeting")
public class AdMeetingController {

    @Autowired
    private AdMeetingService adMeetingService;

    @GetMapping
    public ResponseObject getAllMeetingByIdClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(adMeetingService.getAllMeetingByIdClass(idClass));
    }

    @PostMapping
    public ResponseObject create(@RequestBody AdCreateMeetingRequest request) {
        return new ResponseObject(adMeetingService.create(request));
    }

    @PutMapping
    public ResponseObject update(@RequestBody AdUpdateMeetingRequest request) {
        return new ResponseObject(adMeetingService.update(request));
    }

    @DeleteMapping
    public ResponseObject delete(@RequestParam("id") String id) {
        return new ResponseObject(adMeetingService.delete(id));
    }

    @PostMapping("/change-teacher")
    public ResponseObject changeTeacher(@RequestBody AdChangeTeacherRequest request) {
        return new ResponseObject(adMeetingService.changeTeacher(request));
    }

    @PostMapping("/create-meeting-auto")
    public ResponseObject createMeetingAuto(@RequestBody AdCreateMeetingAutoRequest request) {
        return new ResponseObject(adMeetingService.createMeetingAuto(request));
    }

    @GetMapping("/detail")
    public ResponseObject detailMeeting(@RequestParam("id") String id) {
        return new ResponseObject(adMeetingService.detailMeeting(id));
    }
}
