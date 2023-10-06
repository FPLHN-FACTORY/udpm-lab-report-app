package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassStatisticalResponse;
import com.labreportapp.labreport.core.teacher.service.TeClassService;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher/statistical")
@CrossOrigin(origins = {"*"})
public class TeStatisticalController {

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private TeClassService teClassService;

    @GetMapping("")
    public ResponseObject searchTeClassStatistical(final TeFindClassStatisticalRequest request) {
        request.setIdTeacher(labReportAppSession.getUserId());
        PageableObject<TeClassStatisticalResponse> pageList = teClassService.searchClassStatistical(request);
        return new ResponseObject(pageList);
    }

}
