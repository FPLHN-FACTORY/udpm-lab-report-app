package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodResponse;
import com.labreportapp.labreport.repository.MeetingPeriodRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface AdMeetingPeriodRepository extends MeetingPeriodRepository {

    @Query(value = """
            SELECT * FROM meeting_period ORDER BY start_hour ASC
            """, nativeQuery = true)
    List<AdMeetingPeriodResponse> getAllMeetingPeriod();
}
