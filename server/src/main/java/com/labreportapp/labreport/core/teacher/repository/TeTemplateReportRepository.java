package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface TeTemplateReportRepository extends JpaRepository<TemplateReport, String> {
}
