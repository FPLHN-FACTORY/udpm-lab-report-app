package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.controller.AdUpdateTemplateReportRequest;
import com.labreportapp.labreport.core.admin.repository.AdTemplateReportRepository;
import com.labreportapp.labreport.core.admin.service.AdTemplateReportService;
import com.labreportapp.labreport.entity.TemplateReport;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class AdTemplateReportServiceImpl implements AdTemplateReportService {

    @Autowired
    private AdTemplateReportRepository adTemplateReportRepository;

    @Override
    public TemplateReport getOneTemplateReport() {
        List<TemplateReport> templateReportList = adTemplateReportRepository.findAll();
        if (templateReportList == null || templateReportList.isEmpty()) {
            return null;
        }
        return templateReportList.get(0);
    }

    @Override
    public TemplateReport update(@Valid AdUpdateTemplateReportRequest adUpdateTemplateReportRequest) {
        List<TemplateReport> templateReportList = adTemplateReportRepository.findAll();
        if (templateReportList == null || templateReportList.isEmpty()) {
            TemplateReport templateReport = new TemplateReport();
            templateReport.setDescriptions(adUpdateTemplateReportRequest.getDescriptions());
            return adTemplateReportRepository.save(templateReport);
        } else {
            TemplateReport templateReport = templateReportList.get(0);
            templateReport.setDescriptions(adUpdateTemplateReportRequest.getDescriptions());
            return adTemplateReportRepository.save(templateReport);
        }
    }
}
