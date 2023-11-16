package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.labreport.core.admin.model.response.AdGetActivityResponse;
import com.labreportapp.labreport.core.admin.service.AdActivityService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/activity")
public class AdActivityController {

    @Autowired
    private AdActivityService adActivityService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @GetMapping("")
    public ResponseObject viewActivity(final AdFindActivityRequest request) {
        return new ResponseObject(adActivityService.searchActivity(request));
    }

    @PostMapping
    public ResponseObject creatActivity(@RequestBody final AdCreatActivityRequest request) {
        return new ResponseObject(adActivityService.creatActivity(request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteActivity(@PathVariable("id") String id) {
        return new ResponseObject(adActivityService.deleteActivity(id));
    }

    @PutMapping("/{id}")
    public ResponseObject updateActivity(@PathVariable("id") String id, @RequestBody AdUpdateActivityRequest request) {
        request.setId(id);
        return new ResponseObject(adActivityService.updateActivity(request));
    }

    @GetMapping("/{id}")
    public ResponseObject getOne(@PathVariable("id") String id) {
        return new ResponseObject(adActivityService.getOneByIdActivity(id));
    }

    @GetMapping("/activity-status/{status}")
    public ResponseObject getAllIdByStatus(@PathVariable("status") String status) {
        return new ResponseObject(adActivityService.getAllIdByStatus(status));
    }

    @GetMapping("/activity-semester")
    public ResponseObject getSemester() {
        return new ResponseObject(adActivityService.getSemester());
    }

    @GetMapping("/activity-level")
    public ResponseObject getLevel() {
        return new ResponseObject(adActivityService.getLevel());
    }

    @GetMapping("/id-semester")
    public ResponseObject listActivitySemester(final AdFindActivityRequest adFindClass) {
        List<AdGetActivityResponse> list = adActivityService.getAllByIdSemesters(adFindClass);
        return new ResponseObject(list);
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
