package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.StFindScheduleRequest;
import com.labreportapp.core.student.model.response.StScheduleResponse;
import com.labreportapp.entity.Meeting;
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
              m.meeting_period as meeting_period,
              m.address as address,
              m.type_meeting as type_meeting,
              c.code AS class_code,
              c.start_time as start_time,
              m.descriptions as descriptions
            FROM meeting AS m
            JOIN class AS c ON m.class_id = c.id
            JOIN student_classes AS sc ON c.id = sc.class_id
            WHERE
              sc.student_id = :#{#req.idStudent}
            ORDER BY
              c.start_time ASC  
               AND
                ( :#{#req.searchTime} IS NULL
                OR :#{#req.searchTime} LIKE 'null'
                OR :#{#req.searchTime} LIKE ''
                OR C.start_t ime >= UNIX_TIMESTAMP(NOW())
               AND
                ( :#{#req.searchTime} IS NULL
                OR :#{#req.searchTime} LIKE 'null'
                R :#{#req.searchTime} LIKE ''
                OR C.start_time <= UNIX_TIMESTAMP(DATE_ADD(NOW(), INTERVAL 10 DAY))
               AND
                ( :#{#req.searchTime} IS NULL
                OR :#{#req.searchTime} LIKE 'null'
                OR :#{#req.searchTime} LIKE ''
                OR C.start_time >= UNIX_TIMESTAMP(:#{#req.searchTime}))
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
                              OR C.start_time >= UNIX_TIMESTAMP(NOW())
                            AND
                               ( :#{#req.searchTime} IS NULL
                              OR :#{#req.searchTime} LIKE 'null'
                              OR :#{#req.searchTime} LIKE ''
                              OR C.start_time <= UNIX_TIMESTAMP(DATE_ADD(NOW(), INTERVAL 10 DAY))
                            AND
                               ( :#{#req.searchTime} IS NULL
                              OR :#{#req.searchTime} LIKE 'null'
                              OR :#{#req.searchTime} LIKE ''
                              OR C.start_time >= UNIX_TIMESTAMP(:#{#req.searchTime}))
            """, nativeQuery = true)
    Page<StScheduleResponse> findScheduleByStudent(@Param("req") StFindScheduleRequest req, Pageable pageable);
}

//              AND DATE(FROM_UNIXTIME(C.start_time / 1000)) between CURDATE() and CURDATE()
