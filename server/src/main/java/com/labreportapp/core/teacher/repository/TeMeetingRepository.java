package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface TeMeetingRepository extends JpaRepository<Meeting, String> {

    @Query(value = """
            SELECT DISTINCT 
            m.id as id,
          
            m.descriptions as descriptions,
              m.name as name,
            m.meeting_date as meeting_date
            FROM meeting m
            WHERE m.class_id = :#{#req.idClass}
            ORDER BY m.meeting_date ASC
                     """, countQuery = """
            SELECT COUNT(DISTINCT m.id)
              FROM meeting m
            WHERE m.class_id = :#{#req.idClass}
            """, nativeQuery = true)
    List<TeMeetingRespone> findMeetingByIdClass(@Param("req") TeFindMeetingRequest req);

}
