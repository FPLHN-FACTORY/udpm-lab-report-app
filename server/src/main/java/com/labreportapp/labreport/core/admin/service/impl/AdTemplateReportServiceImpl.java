package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.controller.AdUpdateTemplateReportRequest;
import com.labreportapp.labreport.core.admin.repository.AdTemplateReportRepository;
import com.labreportapp.labreport.core.admin.service.AdTemplateReportService;
import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdTemplateReportServiceImpl implements AdTemplateReportService {
    @Autowired
    private AdTemplateReportRepository adTemplateReportRepository;

    @Override
    public TemplateReport getOneTemplateReport(String id) {
        return adTemplateReportRepository.findById(id).get();
    }

    @Override
    public TemplateReport update(String id, AdUpdateTemplateReportRequest adUpdateTemplateReportRequest) {
        TemplateReport templateReport = adTemplateReportRepository.findById(id).get();
        templateReport.setDescriptions(adUpdateTemplateReportRequest.getDescriptions());
        return adTemplateReportRepository.save(templateReport);
    }
}
