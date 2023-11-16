package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.service.AdTemplateReportService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/template-report")
public class AdTemplateReportController {
    @Autowired
    private AdTemplateReportService adTemplateReportService;

    @GetMapping
    public ResponseObject viewTemplateReport() {
        return new ResponseObject(adTemplateReportService.getOneTemplateReport());
    }

    @PutMapping("/update")
    public ResponseObject updateTemplateReport(@RequestBody AdUpdateTemplateReportRequest adUpdateTemplateReportRequest) {
        return new ResponseObject(adTemplateReportService.update(adUpdateTemplateReportRequest));
    }
}
