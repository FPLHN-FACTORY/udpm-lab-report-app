package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.request.StFindPointAllRequest;
import com.labreportapp.core.student.model.response.StPointAllRespone;
import com.labreportapp.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StPointAllRepository extends JpaRepository<Point,String> {

    @Query(value = """
          SELECT p.id, p.check_point_phase1, p.check_point_phase2, p.final_point
                    FROM point p
                    JOIN class c ON p.class_id = c.id
                    JOIN activity ac ON c.activity_id = ac.id
                    JOIN semester s ON ac.semester_id = s.id
                    WHERE p.student_id = :#{#req.idStudent}
                    AND p.class_id = :#{#req.idClass}
                    AND s.id = :#{#req.idSemester}
          """, nativeQuery = true)
    List<StPointAllRespone> getPointListByStudentInClassAndSemester(@Param("req") StFindPointAllRequest req);
}
