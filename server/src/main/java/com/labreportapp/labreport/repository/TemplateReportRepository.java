package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(TemplateReportRepository.NAME)
public interface TemplateReportRepository extends JpaRepository<TemplateReport, String> {

    String NAME = "BaseTemplateReportRepository";
}
