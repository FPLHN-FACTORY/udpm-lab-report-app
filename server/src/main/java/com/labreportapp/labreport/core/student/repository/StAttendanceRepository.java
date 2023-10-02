package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendanceRespone;
import com.labreportapp.labreport.repository.AttendanceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StAttendanceRepository extends AttendanceRepository {

    @Query(value = """                        
          SELECT DISTINCT m.id, ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt, m.name, m.meeting_date, m.meeting_period, m.type_meeting, m.teacher_id, a.status AS status
          FROM attendance a
          RIGHT JOIN meeting m ON a.meeting_id = m.id
          JOIN class c ON m.class_id = c.id
          JOIN student_classes st ON c.id = st.class_id
          JOIN activity ac ON c.activity_id = ac.id
          JOIN semester s ON ac.semester_id = s.id
          WHERE (a.student_id IS NULL OR a.student_id = :#{#req.idStudent})
          AND st.class_id = :#{#req.idClass}
          GROUP BY m.id,m.name, m.meeting_date, m.meeting_period, m.type_meeting, m.teacher_id, a.status
          ORDER BY m.meeting_date ASC 
            """, nativeQuery = true)
    List<StAttendanceRespone> getAllAttendanceById(@Param("req") StFindAttendanceRequest req);
}
