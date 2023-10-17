package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindTeamRequest;
import com.labreportapp.labreport.core.admin.model.response.AdTeamResponse;
import com.labreportapp.labreport.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Repository
public interface AdTeamRepository extends JpaRepository<Team, String> {
    @Query(" SELECT obj FROM Team obj")
    List<Team> getAllTeam(Pageable pageable);

    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.created_date DESC ) AS stt ,
                obj.id as id,
                obj.name as name,
                obj.subject_name as subjectName,
                obj.created_date as createdDate,
                obj.project_id as project_id,
                obj.class_id as class_id
            FROM team obj 
            WHERE  ( :#{#req.name} IS NULL 
                   OR :#{#req.name} LIKE '' 
                   OR obj.name LIKE %:#{#req.name}% )
                    ORDER BY obj.created_date DESC         
                    """, countQuery = """    
            SELECT COUNT(obj.id) 
            FROM team obj 
            WHERE ( :#{#req.name} IS NULL 
                    OR :#{#req.name} LIKE '' 
                    OR obj.name LIKE %:#{#req.name}% )     
                    ORDER BY obj.created_date DESC       
            """, nativeQuery = true)
    Page<AdTeamResponse> searchTeam(@Param("req") AdFindTeamRequest req, Pageable page);

    @Query(value = "SELECT COUNT(*) FROM project a join team b on a.id=b.project_id WHERE b.id = :id", nativeQuery = true)
    Integer countProjectByTeamId(@Param("id") String id);

}
