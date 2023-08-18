package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.core.teacher.model.response.TeActivityRespone;
import com.labreportapp.entity.Activity;
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
            SELECT a.id as id, a.name as name FROM activity a 
            WHERE
          (:#{#req.idSemester} IS NULL 
           OR :#{#req.idSemester} = '' 
           OR a.semester_id = :#{#req.idSemester})
            """, nativeQuery = true)
    List<TeActivityRespone> getAllByIdSemester(@Param("req") TeFindClassRequest req);

}
