package com.labreportapp.labreport.core.student.controller;


import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.service.StPointDetailService;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student/my-class")
@CrossOrigin("*")
public class StPointDetailController {

    @Autowired
    private StPointDetailService stMyPointClassService;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @GetMapping("/point")
    public ResponseObject getAllPointMyClass(@RequestParam("idClass") String idClass) {
        return new ResponseObject(stMyPointClassService.getMyPointClass(idClass, labReportAppSession.getUserId()));
    }

}
