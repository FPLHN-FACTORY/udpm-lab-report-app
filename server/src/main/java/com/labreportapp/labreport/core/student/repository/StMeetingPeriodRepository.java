package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.entity.MeetingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface StMeetingPeriodRepository extends JpaRepository<MeetingPeriod, String> {
}
