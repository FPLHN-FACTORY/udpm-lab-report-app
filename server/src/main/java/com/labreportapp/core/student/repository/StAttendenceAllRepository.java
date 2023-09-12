package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.core.student.model.response.StAttendenceAllResponse;
import com.labreportapp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StAttendenceAllRepository extends JpaRepository<Attendance, String> {

  @Query(value = """
          SELECT DISTINCT m.id, ROW_NUMBER() OVER(ORDER BY m.meeting_date ASC) AS stt, m.name, m.meeting_date, m.meeting_period, m.type_meeting, max(a.status) AS status
          FROM attendance a
          RIGHT JOIN meeting m ON a.meeting_id = m.id
          JOIN student_classes st ON m.class_id = st.class_id
          JOIN class c ON st.class_id = c.id
          JOIN activity ac ON c.activity_id = ac.id
          JOIN semester s ON ac.semester_id = s.id
          WHERE st.student_id = :#{#req.idStudent}
          AND st.class_id = :#{#req.idClass}
          AND s.id = :#{#req.idSemester}
          GROUP BY m.id
          ORDER BY m.meeting_date ASC 
          """, nativeQuery = true)
  List<StAttendenceAllResponse> getAttendenceListByStudentInClassAndSemester(@Param("req") StFindAttendenceAllRequest req);


}
