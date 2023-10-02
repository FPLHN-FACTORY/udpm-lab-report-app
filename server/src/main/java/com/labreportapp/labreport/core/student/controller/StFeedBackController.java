package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StCheckFeedBackRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.request.StStudentListFeedBackRequest;
import com.labreportapp.labreport.core.student.model.response.StMyClassCustom;
import com.labreportapp.labreport.core.student.service.StFeedBackService;
import com.labreportapp.labreport.core.student.service.StMyClassService;
import com.labreportapp.labreport.util.SemesterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/student/feedback")
@CrossOrigin("*")
public class StFeedBackController {

    @Autowired
    private StFeedBackService stFeedBackService;

    @Autowired
    private StMyClassService stMyClassService;

    @Autowired
    private SemesterHelper semesterHelper;

    @GetMapping("/check")
    public ResponseObject checkFeedBack() {
        return new ResponseObject(stFeedBackService.checkFeedBack());
    }

    @GetMapping("/get-semester-current")
    public ResponseObject getSemesterCurrent() {
        return new ResponseObject(stFeedBackService.getSemesterCurrent());
    }

    @GetMapping("/get-all-class-feedback")
    public ResponseObject checkFeedBack(final StFindClassRequest request) {
        request.setSemesterId(semesterHelper.getSemesterCurrent());
        return new ResponseObject(stFeedBackService.getAllClass(request));
    }

    @PostMapping("/create-feedback")
    public ResponseObject createFeedback(@RequestBody StStudentListFeedBackRequest request) {
        return new ResponseObject(stFeedBackService.createFeedBack(request));
    }

}
