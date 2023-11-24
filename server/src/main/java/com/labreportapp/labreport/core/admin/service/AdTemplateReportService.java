package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateTemplateReportRequest;
import com.labreportapp.labreport.entity.TemplateReport;
import jakarta.validation.Valid;

public interface AdTemplateReportService {

    TemplateReport getOneTemplateReport();

    TemplateReport update(@Valid AdUpdateTemplateReportRequest adUpdateTemplateReportRequest);
}
