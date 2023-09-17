package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindPointRequest;
import com.labreportapp.labreport.core.teacher.model.response.TePointRespone;
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
            SELECT 
                p.id as id,
                p.student_id as idStudent,
                p.check_point_phase1 as check_point_phase1,
                p.check_point_phase2 as check_point_phase2,
                p.final_point as final_point,
                p.class_id as class_id
            FROM point p
            WHERE p.class_id = :#{#idClass}
            """, nativeQuery = true)
    List<TePointRespone> getAllPointByIdClass(@Param("idClass") String idClass);

    @Query(value = """
            SELECT * FROM point p
            WHERE p.class_id = :#{#idClass}
            """, nativeQuery = true)
    List<Point> getAllPointByIdClassImport(@Param("idClass") String idClass);

    @Query(value = """
            SELECT 
                p.id as id,
                p.student_id as idStudent,
                p.check_point_phase1 as check_point_phase1,
                p.check_point_phase2 as check_point_phase2,
                p.final_point as final_point,
                p.class_id as class_id
            FROM point p
            WHERE p.class_id = :#{#req.idClass} and p.student_id = :#{#req.idStudent}
            """, nativeQuery = true)
    Optional<TePointRespone> getPointIdClassIdStudent(@Param("req") TeFindPointRequest req);

}
