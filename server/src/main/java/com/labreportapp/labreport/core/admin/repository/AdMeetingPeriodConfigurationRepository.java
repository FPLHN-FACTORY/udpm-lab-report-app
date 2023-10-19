package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindMeetingConfigurationRequest;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodConfigurationResponse;
import com.labreportapp.labreport.entity.MeetingPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Repository
public interface AdMeetingPeriodConfigurationRepository extends JpaRepository<MeetingPeriod, String> {
    @Query(" SELECT obj FROM MeetingPeriod obj")
    List<MeetingPeriod> getAllMeetingPeriod(Pageable pageable);

    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.created_date DESC ) AS stt ,
            obj.id,
            obj.name as name,
            obj.start_hour as startHour,
            obj.start_minute as startMinute,
            obj.end_hour as endHour,
            obj.end_minute as endMinute
            FROM meeting_period obj 
            WHERE  ( :#{#req.name} IS NULL 
                   OR :#{#req.name} LIKE '' 
                   OR obj.name LIKE %:#{#req.name}% )
                    ORDER BY obj.created_date DESC         
                    """, countQuery = """    
            SELECT COUNT(obj.id) 
            FROM meeting_period obj 
            WHERE ( :#{#req.name} IS NULL 
                    OR :#{#req.name} LIKE '' 
                    OR obj.name LIKE %:#{#req.name}% )     
                    ORDER BY obj.created_date DESC       
            """, nativeQuery = true)
    Page<AdMeetingPeriodConfigurationResponse> searchMeetingPeriod(@Param("req") AdFindMeetingConfigurationRequest req, Pageable page);

    @Query(value = "SELECT COUNT(*) FROM meeting a join meeting_period b on a.meeting_period = b.id WHERE b.id  = :id", nativeQuery = true)
    Integer countMeetingByMeetingPeriodId(@Param("id") String id);
}
