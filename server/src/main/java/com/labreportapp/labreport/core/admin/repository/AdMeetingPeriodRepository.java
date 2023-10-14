package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodResponse;
import com.labreportapp.labreport.repository.MeetingPeriodRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdMeetingPeriodRepository extends MeetingPeriodRepository {

    @Query(value = """
            SELECT * FROM meeting_period ORDER BY start_hour ASC
            """, nativeQuery = true)
    List<AdMeetingPeriodResponse> getAllMeetingPeriod();
}
