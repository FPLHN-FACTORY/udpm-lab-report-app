package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.controller.AdUpdateTemplateReportRequest;
import com.labreportapp.labreport.entity.TemplateReport;

public interface AdTemplateReportService {
    TemplateReport getOneTemplateReport(String id);

    TemplateReport update(String id, AdUpdateTemplateReportRequest adUpdateTemplateReportRequest);
}
