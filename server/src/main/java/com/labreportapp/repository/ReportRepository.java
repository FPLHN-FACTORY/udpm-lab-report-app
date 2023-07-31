package com.labreportapp.repository;

import com.labreportapp.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ReportRepository.NAME)
public interface ReportRepository extends JpaRepository<Report, String> {

    String NAME = "BaseReportRepository";
}
