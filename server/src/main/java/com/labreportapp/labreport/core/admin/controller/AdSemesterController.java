package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdCreateSemesterRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindSemesterRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateSemesterRequest;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.labreport.core.admin.service.AdSemesterService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.Semester;
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

@RestController
@RequestMapping("/admin/semester")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdSemesterController {

    @Autowired
    private AdSemesterService adSemesterService;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllSemester(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<Semester> semesterList = adSemesterService.findAllSermester(pageResquest);
        return ResponseEntity.ok(semesterList);
    }

    @GetMapping("")
    public ResponseObject viewSemester(@ModelAttribute final AdFindSemesterRequest request) {
        return new ResponseObject((adSemesterService.searchSemester(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchSemester(final AdFindSemesterRequest request) {
        PageableObject<AdSemesterResponse> listSemester = adSemesterService.searchSemester(request);
        return new ResponseObject(listSemester);
    }

    @PostMapping("/add")
    public ResponseObject addSemester(@RequestBody AdCreateSemesterRequest obj) {
        return new ResponseObject(adSemesterService.createSermester(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteSemester(@PathVariable("id") String id) {
        return new ResponseObject(adSemesterService.deleteSemester(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateSemester(@PathVariable("id") String id,
                                         @RequestBody AdUpdateSemesterRequest obj) {
        obj.setId(id);
        return new ResponseObject(adSemesterService.updateSermester(obj));
    }

    @PutMapping("/update-status-feedback/{id}")
    public ResponseObject updateStatusFeedback(@PathVariable("id") String id) {
        return new ResponseObject(adSemesterService.updateStatusFeedback(id));
    }

    @GetMapping("/get-all-semesters")
    public ResponseObject getAllSemester() {
        List<AdSemesterResponse> listSemester = adSemesterService.getAllSemesters();
        return new ResponseObject(listSemester);
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
