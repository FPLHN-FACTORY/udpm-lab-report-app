package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindTeamRequest;
import com.labreportapp.labreport.core.admin.model.response.AdTeamResponse;
import com.labreportapp.labreport.entity.TeamFactory;
import com.labreportapp.labreport.repository.TeamFactoryRepository;
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
public interface AdTeamRepository extends TeamFactoryRepository {
    @Query(" SELECT obj FROM TeamFactory obj")
    List<TeamFactory> getAllTeam(Pageable pageable);

    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.created_date DESC ) AS stt ,
                obj.id as id,
                obj.name as name,
                obj.descriptions as descriptions
            FROM team_factory obj 
            WHERE  ( :#{#req.name} IS NULL 
                   OR :#{#req.name} LIKE '' 
                   OR obj.name LIKE %:#{#req.name}% )
                    ORDER BY obj.created_date DESC         
                    """, countQuery = """    
            SELECT COUNT(obj.id) 
            FROM team_factory obj 
            WHERE ( :#{#req.name} IS NULL 
                    OR :#{#req.name} LIKE '' 
                    OR obj.name LIKE %:#{#req.name}% )     
                    ORDER BY obj.created_date DESC       
            """, nativeQuery = true)
    Page<AdTeamResponse> searchTeam(@Param("req") AdFindTeamRequest req, Pageable page);

    @Query(value = "SELECT COUNT(*) FROM member_team_factory a join team_factory b on a.team_factory_id=b.id WHERE b.id = :id", nativeQuery = true)
    Integer countMemberTeamByTeamId(@Param("id") String id);

}
