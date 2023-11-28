package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRequestResponse;
import com.labreportapp.labreport.entity.MeetingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hieundph25894 - duchieu212
 */
@Repository
public interface TeMeetingRequestRepository extends JpaRepository<MeetingRequest, String> {

    @Query(value = """
            SELECT * FROM meeting_request mr WHERE mr.class_id = :idClass AND mr.status_meeting_request = 2
            """, nativeQuery = true)
    List<MeetingRequest> getAllByClassIdAndStatusReject(@Param("idClass") String idClass);

    @Query(value = """
                   SELECT  
                    m.id AS id,
                    m.name AS name,
                    m.meeting_date AS meeting_date,
                    m.type_meeting AS type_meeting,
                    m.meeting_period AS id_meeting_period,
                    mp.name AS meeting_period, mp.start_hour AS start_hour, mp.start_minute AS start_minute,
                    mp.end_hour AS end_hour, mp.end_minute AS end_minute,
                    m.teacher_id AS teacher_id, 
                    m.status_meeting_request as status_meeting_request
               FROM meeting_request m
               LEFT JOIN meeting_period mp ON mp.id = m.meeting_period
                JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#req.idClass} AND
                (:#{#req.statusMeetingRequest} IS NULL OR :#{#req.statusMeetingRequest} LIKE 'null'
                 OR  m.status_meeting_request = :#{#req.statusMeetingRequest})
                ORDER BY m.meeting_date DESC, mp.name DESC;
                """, nativeQuery = true)
    Page<TeMeetingRequestResponse> getAllByClassIdAndStatus(@Param("req") TeFindMeetingRequestRequest req, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
              UPDATE meeting_request a
              JOIN (
                  SELECT m.id, ROW_NUMBER() OVER (ORDER BY m.meeting_date ASC, mp.start_hour ASC) AS row_num
                  FROM meeting_request m
                   JOIN meeting_period mp ON mp.id = m.meeting_period
                  WHERE class_id = :idClass
              ) AS subquery
              ON a.id = subquery.id
              SET a.name = CONCAT('Buổi ', subquery.row_num);
            """, nativeQuery = true)
    void updateNameMeetingRequest(@Param("idClass") String idClass);

}
