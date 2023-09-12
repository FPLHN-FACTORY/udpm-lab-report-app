package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.core.student.model.response.StAttendanceRespone;
import com.labreportapp.entity.Attendance;
import com.labreportapp.repository.AttendanceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StAttendanceRepository extends AttendanceRepository {

    @Query(value = """                        
          SELECT DISTINCT m.id, ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt, m.name, m.meeting_date, m.meeting_period, m.type_meeting, max(a.status) AS status
          FROM attendance a
          RIGHT JOIN meeting m ON a.meeting_id = m.id
          JOIN student_classes st ON m.class_id = st.class_id
          JOIN class c ON st.class_id = c.id
          WHERE st.student_id = :#{#req.idStudent}
          AND st.class_id = :#{#req.idClass}
          GROUP BY m.id
          ORDER BY m.meeting_date ASC 
            """, nativeQuery = true)
    List<StAttendanceRespone> getAllAttendanceById(@Param("req") StFindAttendanceRequest req);
}
