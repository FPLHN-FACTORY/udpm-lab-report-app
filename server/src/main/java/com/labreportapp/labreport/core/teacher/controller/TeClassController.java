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
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/class")
@CrossOrigin(origins = {"*"})
public class TeClassController {

    @Autowired
    private TeClassService teClassService;

    @Autowired
    private LabReportAppSession labReportAppSession;

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

    @PostMapping("/pass-random")
    public ResponseObject randomPassClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(teClassService.randomPassword(idClass));
    }

    @PostMapping("/pass")
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
}
