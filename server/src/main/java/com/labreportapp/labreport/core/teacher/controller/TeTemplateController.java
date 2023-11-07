package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.service.TeTemplateReportService;
import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
@RequestMapping("/teacher-template-report")
@CrossOrigin(origins = {"*"})
public class TeTemplateController {

    @Autowired
    private TeTemplateReportService teTemplateReportService;

    @GetMapping("")
    public ResponseObject getTemplateReport() {
        TemplateReport templateReport = teTemplateReportService.getTemplateReport();
        return new ResponseObject(templateReport);
    }
}
