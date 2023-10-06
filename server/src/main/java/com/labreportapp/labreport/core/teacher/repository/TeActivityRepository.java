package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeActivityResponse;
import com.labreportapp.labreport.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface TeActivityRepository extends JpaRepository<Activity, String> {

    @Query(value = """
              SELECT a.id as id, a.name as name, a.start_time as start_time, a.end_time as end_time FROM activity a 
              WHERE
            (:#{#req.idSemester} IS NULL 
             OR :#{#req.idSemester} = '' 
             OR a.semester_id = :#{#req.idSemester})
             ORDER BY a.created_date ASC
              """, nativeQuery = true)
    List<TeActivityResponse> getAllByIdSemester(@Param("req") TeFindClassRequest req);

}
