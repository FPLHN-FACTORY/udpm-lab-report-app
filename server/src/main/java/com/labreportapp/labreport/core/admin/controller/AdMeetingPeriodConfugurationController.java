package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodConfigurationResponse;
import com.labreportapp.labreport.core.admin.service.AdMeetingPeriodConfigurationService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.MeetingPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/admin/meeting-period-configiration")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdMeetingPeriodConfugurationController {
    @Autowired
    private AdMeetingPeriodConfigurationService adMeetingPeriodConfigurationService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllMeetingPeriod(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<MeetingPeriod> MeetingPeriodList = adMeetingPeriodConfigurationService.findAllMeetingPeriod(pageResquest);
        return ResponseEntity.ok(MeetingPeriodList);
    }

    @GetMapping("")
    public ResponseObject viewMeetingPeriod(@ModelAttribute final AdFindMeetingConfigurationRequest request) {
        return new ResponseObject((adMeetingPeriodConfigurationService.searchMeetingPeriod(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchMeetingPeriod(final AdFindMeetingConfigurationRequest request) {
        PageableObject<AdMeetingPeriodConfigurationResponse> listMeetingPeriod = adMeetingPeriodConfigurationService.searchMeetingPeriod(request);
        return new ResponseObject(listMeetingPeriod);
    }

    @PostMapping("/add")
    public ResponseObject addMeetingPeriod(@RequestBody AdCreateMeetingPeriodRequest obj) {
        return new ResponseObject(adMeetingPeriodConfigurationService.createMeetingPeriod(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteMeetingPeriod(@PathVariable("id") String id) {
        return new ResponseObject(adMeetingPeriodConfigurationService.deleteMeetingPeriod(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateMeetingPeriod(@PathVariable("id") String id,
                                      @RequestBody AdUpdateMeetingPeriodRequest obj) {
        obj.setId(id);
        return new ResponseObject(adMeetingPeriodConfigurationService.updateMeetingPeriod(obj));
    }


}
