package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.service.StTemplateReportService;
import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/student-template-report")
@CrossOrigin(origins = {"*"})
public class StTemplateReportController {
    @Autowired
    private StTemplateReportService stTemplateReportService;

    @GetMapping("")
    public ResponseObject getTemplateReport() {
        TemplateReport templateReport = stTemplateReportService.getTemplateReport();
        return new ResponseObject(templateReport);
    }
}
