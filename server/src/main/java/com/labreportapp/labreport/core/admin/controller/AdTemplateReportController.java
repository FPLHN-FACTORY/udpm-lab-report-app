package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.service.AdTemplateReportService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/template-report")
@CrossOrigin(origins = {"*"})
public class AdTemplateReportController {
    @Autowired
    private AdTemplateReportService adTemplateReportService;

    @GetMapping("/{id}")
    public ResponseObject viewTemplateReport(@PathVariable String id) {
        return new ResponseObject(adTemplateReportService.getOneTemplateReport(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateTemplateReport(@PathVariable String id, @RequestBody AdUpdateTemplateReportRequest adUpdateTemplateReportRequest) {
        return new ResponseObject(adTemplateReportService.update(id, adUpdateTemplateReportRequest));
    }
}
