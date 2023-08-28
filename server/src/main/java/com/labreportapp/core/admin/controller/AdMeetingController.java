package com.labreportapp.core.admin.controller;

import com.labreportapp.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.core.admin.service.AdMeetingService;
import com.labreportapp.core.common.base.ResponseObject;
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
@RequestMapping("/admin/meeting")
@CrossOrigin("*")
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
}
