package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.MeetingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(MeetingPeriodRepository.NAME)
public interface MeetingPeriodRepository extends JpaRepository<MeetingPeriod, String> {

    String NAME = "BaseMeetingPeriodRepository";
}
