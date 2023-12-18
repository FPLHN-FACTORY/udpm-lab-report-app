package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSelectRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSentStudentRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindUpdateStatusClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeClassSentStudentRespone;
import com.labreportapp.labreport.core.teacher.service.TeClassService;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.core.admin.service.AdGroupProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/api/teacher/class")
public class TeClassController {

    @Autowired
    private TeClassService teClassService;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @Autowired
    private AdGroupProjectService adGroupProjectService;

    @GetMapping("")
    public ResponseObject searchTeClass(final TeFindClassRequest teFindClass) {
        teFindClass.setIdTeacher(labReportAppSession.getUserId());
        PageableObject<TeClassResponse> pageList = teClassService.searchTeacherClass(teFindClass);
        return new ResponseObject(pageList);
    }

    @GetMapping("/class-sent")
    public ResponseObject classSentStudent(final TeFindClassSentStudentRequest request) {
        PageableObject<TeClassSentStudentRespone> pageList = teClassService.findClassBySentStudent(request);
        return new ResponseObject(pageList);
    }

    @GetMapping("/{id}")
    public ResponseObject detailClass(@PathVariable("id") String id) {
        return new ResponseObject(teClassService.findClassById(id));
    }

    @GetMapping("/semester-nearest/{id}")
    public ResponseObject getClassClosestToTheDateToSemester(@PathVariable("id") String id) {
        return new ResponseObject(teClassService.getClassClosestToTheDateToSemester(id));
    }

    @PutMapping("/pass-random")
    public ResponseObject randomPassClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(teClassService.randomPassword(idClass));
    }

    @PutMapping("/pass")
    public ResponseObject updateStatusClass(@RequestBody TeFindUpdateStatusClassRequest request) {
        return new ResponseObject(teClassService.updateStatusClass(request));
    }

    /*
    todo hàm làm select của màn feedback teacher
    input : id semester, id activity
     */
    @GetMapping("/filter-class")
    public ResponseObject listClassIdActivityIdSemester(final TeFindClassSelectRequest request) {
        request.setIdTeacher(labReportAppSession.getUserId());
        return new ResponseObject(teClassService.listClass(request));
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "50") Integer size,
                                         @RequestParam(name = "idClass", defaultValue = "") String idClass
    ) {
        String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
        String pathFile = loggerUtil.getPathFileSendLogStreamClass(nameSemester);
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setCodeClass(loggerUtil.getCodeClassByIdClass(idClass));
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseObject getAllGroupToProjectManagement() {
        return new ResponseObject(adGroupProjectService.getAllGroupToProjectManagement());
    }
}
