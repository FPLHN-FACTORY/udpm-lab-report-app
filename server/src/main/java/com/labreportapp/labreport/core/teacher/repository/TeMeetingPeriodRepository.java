package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.entity.MeetingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface TeMeetingPeriodRepository extends JpaRepository<MeetingPeriod, String> {
}
