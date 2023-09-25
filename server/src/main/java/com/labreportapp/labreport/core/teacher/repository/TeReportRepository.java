package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface TeReportRepository extends JpaRepository<Report, String> {
}
