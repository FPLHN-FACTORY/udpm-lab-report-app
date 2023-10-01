package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.student.repository.StTemplateReportRepository;
import com.labreportapp.labreport.core.student.service.StTemplateReportService;
import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author quynhncph26201
 */
@Service
public class StTemplateReportServiceImpl implements StTemplateReportService {
    @Autowired
    private StTemplateReportRepository repository;
    @Override
    public TemplateReport getTemplateReport() {
        TemplateReport templateReport = repository.findAll().get(0);
        if (templateReport == null) {
            return null;
        }
        return templateReport;
    }
}
