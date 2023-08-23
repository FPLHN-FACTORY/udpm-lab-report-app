package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Repository
public interface TeMeetingRepository extends JpaRepository<Meeting, String> {

    @Query(value = """
            SELECT DISTINCT 
            m.id as id,
            m.name as name,
            m.descriptions as descriptions,
            m.meeting_date as meeting_date,
            m.type_meeting as type_meeting,
            m.meeting_period as meeting_period,
            m.class_id as class_id
            FROM meeting m
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#req.idClass}
            ORDER BY m.meeting_date DESC
                     """, countQuery = """
            SELECT COUNT(DISTINCT m.id)
              FROM meeting m
            JOIN class c ON c.id = m.class_id
            WHERE m.class_id = :#{#req.idClass}
            """, nativeQuery = true)
    List<TeMeetingRespone> findMeetingByIdClass(@Param("req") TeFindMeetingRequest req);

    Integer countMeetingByClassId(String idClass);

    @Query(value = """
            SELECT  m.id as id,
            m.name as name,
            m.descriptions as descriptions,
            m.meeting_date as meeting_date,
            m.type_meeting as type_meeting,
            m.meeting_period as meeting_period,
             m.class_id as class_id
            FROM meeting m
            WHERE m.id = :#{#req.idMeeting}
                     """, nativeQuery = true)
    Optional<TeMeetingRespone> searchMeetingByIdMeeting(@Param("req") TeFindMeetingRequest req);

}
