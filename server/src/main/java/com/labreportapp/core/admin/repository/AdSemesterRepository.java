package com.labreportapp.core.admin.repository;

import com.labreportapp.core.admin.model.request.AdFindSemesterRequest;
import com.labreportapp.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Semester;
import com.labreportapp.repository.SemesterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdSemesterRepository extends SemesterRepository {

    @Query(" SELECT obj FROM Semester obj")
    List<Semester> getAllSemester(Pageable pageable);

    @Query("SELECT COUNT(obj) FROM Activity obj WHERE obj.semesterId = :id")
    Integer countActivitiesBySemesterId(@Param("id") String id);

    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.last_modified_date DESC ) AS stt ,
            obj.id,
            obj.name,
            obj.start_time,
            obj.end_time
            FROM semester obj 
            WHERE  ( :#{#req.name} IS NULL 
                   OR :#{#req.name} LIKE '' 
                   OR obj.name LIKE %:#{#req.name}% )
                    ORDER BY obj.last_modified_date DESC         
                    """, countQuery = """    
            SELECT COUNT(obj.id) 
            FROM semester obj 
            WHERE   ( :#{#req.name} IS NULL 
                    OR :#{#req.name} LIKE '' 
                    OR obj.name LIKE %:#{#req.name}% )     
            """, nativeQuery = true)
    Page<AdSemesterResponse> searchSemester(@Param("req") AdFindSemesterRequest req, Pageable page);
}
