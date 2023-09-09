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
          SELECT DISTINCT m.id, DENSE_RANK() OVER(ORDER BY m.meeting_date asc) as stt,  m.name, m.meeting_date, m.meeting_period, m.type_meeting, a.status
          FROM  meeting m
          LEFT JOIN attendance a ON m.id = a.meeting_id
          JOIN student_classes sc ON m.class_id = sc.class_id
          JOIN class c ON sc.class_id = c.id
          JOIN activity ac ON c.activity_id = ac.id
          JOIN semester s ON ac.semester_id = s.id
          WHERE sc.student_id = :#{#req.idStudent}
          AND sc.class_id = :#{#req.idClass}
          AND s.id = :#{#req.idSemester}
          ORDER BY m.meeting_date asc
          """, nativeQuery = true)
  List<StAttendenceAllResponse> getAttendenceListByStudentInClassAndSemester(@Param("req") StFindAttendenceAllRequest req);


}
