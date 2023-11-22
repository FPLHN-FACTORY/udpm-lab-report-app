package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingPeriodRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindMeetingConfigurationRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingPeriodRequest;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodConfigurationResponse;
import com.labreportapp.labreport.core.admin.service.AdMeetingPeriodConfigurationService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/api/admin/meeting-period-configiration")
public class AdMeetingPeriodConfugurationController {

    @Autowired
    private AdMeetingPeriodConfigurationService adMeetingPeriodConfigurationService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

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

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() {
        String pathFile = loggerUtil.getPathFileSendLogScreen("");
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "50") Integer size
    ) {
        String pathFile = loggerUtil.getPathFileSendLogScreen("");
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }
}
