package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.repository.TeTemplateReportRepository;
import com.labreportapp.labreport.core.teacher.service.TeTemplateReportService;
import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hieundph25894
 */
@Service
public class TeTemplateReportServiceImpl implements TeTemplateReportService {

    @Autowired
    private TeTemplateReportRepository teTemplateReportRepository;

    @Override
    public TemplateReport getTemplateReport() {
        TemplateReport templateReport = teTemplateReportRepository.findAll().get(0);
        if (templateReport == null) {
            return null;
        }
        return templateReport;
    }
}
