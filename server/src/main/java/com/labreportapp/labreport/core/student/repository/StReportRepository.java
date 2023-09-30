package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.labreport.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Repository
public interface StReportRepository extends JpaRepository<Report, String> {

}

