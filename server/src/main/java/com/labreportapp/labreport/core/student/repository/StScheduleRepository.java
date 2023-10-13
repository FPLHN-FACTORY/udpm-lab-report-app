package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StFindScheduleRequest;
import com.labreportapp.labreport.core.student.model.response.StScheduleResponse;
import com.labreportapp.labreport.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StScheduleRepository extends JpaRepository<Meeting, String> {

    @Query(" SELECT obj FROM Meeting obj")
    List<Meeting> getAllMeeting(Pageable pageable);

    @Query(value = """
            SELECT distinct
              m.id,
              m.name as meeting_name,
              m.meeting_date as meeting_date,
              mp.name as meeting_period,
              m.address as address,
              m.type_meeting as type_meeting,
              mp.name as meeting_period, mp.start_hour as start_hour, mp.start_minute as start_minute ,
              mp.end_hour as end_hour, mp.end_minute as end_minute,
              c.code AS class_code,
              c.start_time as start_time,
              m.descriptions as descriptions,
              sc.student_id
            FROM meeting AS m
            JOIN meeting_period AS mp ON mp.id = m.meeting_period
            JOIN class AS c ON m.class_id = c.id
            JOIN student_classes AS sc ON c.id = sc.class_id
            WHERE
              sc.student_id = :#{#req.idStudent}
               AND
                ( :#{#req.searchTime} IS NULL
                                   OR :#{#req.searchTime} LIKE 'null'
                                   OR :#{#req.searchTime} LIKE ''
                                   OR :#{#req.searchTime} = 0
                                   OR DATE(FROM_UNIXTIME(m.meeting_date/ 1000)) between CURDATE() and
                                   DATE_ADD(CURDATE() , INTERVAL :#{#req.searchTime} DAY) 
                                   OR DATE(FROM_UNIXTIME(m.meeting_date/ 1000)) between 
                                   DATE_ADD(CURDATE() , INTERVAL :#{#req.searchTime} DAY) and  CURDATE()
                                   )
              ORDER BY m.meeting_date ASC
              """, countQuery = """   
            SELECT COUNT(DISTINCT m.id)
            FROM meeting AS m
            JOIN class AS c ON m.class_id = c.id
            JOIN student_classes AS sc ON c.id = sc.class_id
            WHERE
              sc.student_id = :#{#req.idStudent}   
              AND
                ( :#{#req.searchTime} IS NULL
                                   OR :#{#req.searchTime} LIKE 'null'
                                   OR :#{#req.searchTime} LIKE ''
                                   OR :#{#req.searchTime} = 0
                                   OR DATE(FROM_UNIXTIME(m.meeting_date/ 1000)) between CURDATE() and
                                   DATE_ADD(CURDATE() , INTERVAL :#{#req.searchTime} DAY) 
                                   OR DATE(FROM_UNIXTIME(m.meeting_date/ 1000)) between 
                                   DATE_ADD(CURDATE() , INTERVAL :#{#req.searchTime} DAY) and  CURDATE()
                                   )
            """, nativeQuery = true)
    Page<StScheduleResponse> findScheduleByStudent(@Param("req") StFindScheduleRequest req, Pageable pageable);
}

