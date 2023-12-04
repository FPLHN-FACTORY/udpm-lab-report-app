package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindPointRequest;
import com.labreportapp.labreport.core.teacher.model.response.TePointCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TePointResponse;
import com.labreportapp.labreport.entity.Point;
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
public interface TePointRepository extends JpaRepository<Point, String> {

    @Query(value = """
            WITH count_attendance AS (
                 SELECT sc.student_id AS idStudent,
            		    COALESCE(COUNT(a.student_id), 0) AS soBuoiDiHoc
                 FROM student_classes sc
             	LEFT JOIN attendance a ON a.student_id = sc.student_id AND a.status = 0
                 WHERE sc.class_id = :#{#idClass}
                 GROUP BY sc.student_id
            ), count_all_meeting AS(
            	    SELECT m.class_id AS idClass,
            			COALESCE(COUNT(m.id), 0) AS soBuoiPhaiHoc
            	    FROM meeting m
            	    WHERE m.class_id = :#{#idClass} AND m.status_meeting = 0
                 GROUP BY m.class_id
            )
            		  SELECT ROW_NUMBER() OVER(ORDER BY t.name ASC) AS stt,
            		     sc.id AS id_studentClasses,
            			 p.id AS id_point,
                         sc.student_id AS id_student,
                         sc.email AS email,
                         t.name AS name_team,
                         p.check_point_phase1 AS check_point_phase1,
                         p.check_point_phase2 AS check_point_phase2,
                         p.final_point AS final_point,
                         sc.class_id AS class_id,
                         ca.soBuoiDiHoc AS so_buoi_di,
                         cm.soBuoiPhaiHoc AS so_buoi_hoc
            			FROM student_classes sc
                        LEFT JOIN point p ON p.student_id = sc.student_id
                        LEFT JOIN class c ON c.id = sc.class_id
                        LEFT JOIN team t ON t.id = sc.team_id
                        LEFT JOIN meeting m ON m.class_id = sc.class_id
                        LEFT JOIN count_attendance ca ON ca.idStudent = sc.student_id
                        LEFT JOIN count_all_meeting cm ON cm.idClass = sc.class_id
                        WHERE m.status_meeting = 0 AND sc.class_id=  :#{#idClass}
                        GROUP BY p.id, sc.id, sc.student_id,p.check_point_phase1, p.check_point_phase2, p.final_point,
                        sc.class_id, sc.email, t.name, ca.soBuoiDiHoc, cm.soBuoiPhaiHoc
                        ORDER BY t.name ASC
             """, nativeQuery = true)
    List<TePointResponse> getAllPointByIdClass(@Param("idClass") String idClass);

    @Query(value = """
                   SELECT sc.student_id AS id_student,
                   sc.email AS email,
                   p.final_point AS final_point
                   FROM student_classes sc
                   LEFT JOIN point p ON p.student_id = sc.student_id
                   JOIN class c ON c.id = sc.class_id
                   WHERE sc.class_id = :#{#idClass}
                   AND status = 0
                   GROUP BY sc.student_id, sc.email, p.final_point, p.id
            """, nativeQuery = true)
    List<TePointCustomResponse> getAllPointCustomByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT * FROM point p
            WHERE p.class_id = :#{#idClass}
            """, nativeQuery = true)
    List<Point> getAllPointByIdClassImport(@Param("idClass") String idClass);

    @Query(value = """
            SELECT p.id AS id,
                p.student_id AS idStudent,
                p.check_point_phase1 AS check_point_phase1,
                p.check_point_phase2 AS check_point_phase2,
                p.final_point AS final_point,
                p.class_id AS class_id
            FROM point p
            WHERE p.class_id = :#{#req.idClass} AND p.student_id = :#{#req.idStudent}
            """, nativeQuery = true)
    Optional<TePointResponse> getPointIdClassIdStudent(@Param("req") TeFindPointRequest req);

    @Query(value = """
            SELECT * FROM point p WHERE p.class_id = :#{#idClass}
            """, nativeQuery = true)
    List<Point> getAllByClassId(@Param("idClass") String idClass);
}
