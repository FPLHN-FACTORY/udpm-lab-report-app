package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.entity.TemplateReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author quynhncph26201
 */
@Service

@Repository
public interface StTemplateReportRepository extends JpaRepository<TemplateReport, String> {
}
