package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.core.student.model.request.StFindTeamFactoryRequest;
import com.labreportapp.labreport.core.student.model.response.StTeamFactoryResponse;
import com.labreportapp.labreport.core.student.model.response.StTemplateMemberFactoryResponse;
import com.labreportapp.labreport.entity.TeamFactory;
import com.labreportapp.labreport.repository.TeamFactoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Repository
public interface StTeamFactoryRepository extends TeamFactoryRepository {

    @Query(" SELECT obj FROM TeamFactory obj")
    List<TeamFactory> getAllTeam(Pageable pageable);

    @Query(value = """
            SELECT COUNT(DISTINCT id) FROM member_team_factory WHERE team_factory_id = :idTeam
            """, nativeQuery = true)
    Integer countNumberMemberOfTeam(@Param("idTeam") String idTeam);

    @Query(value = """
            SELECT DISTINCT b.member_id FROM member_team_factory a 
            JOIN member_factory b ON b.id = a.member_factory_id
            WHERE a.team_factory_id = :idTeam
            """, nativeQuery = true)
    List<String> getAllMemberOfTeam(@Param("idTeam") String idTeam);

    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.created_date DESC ) AS stt,
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
    Page<StTeamFactoryResponse> searchTeam(@Param("req") StFindTeamFactoryRequest req, Pageable page);


    @Query(value = """
            SELECT DISTINCT a.id, a.member_id, b.id AS id_member_team_factory FROM member_factory a 
            JOIN member_team_factory b ON a.id = b.member_factory_id
            JOIN team_factory c ON c.id = b.team_factory_id
            WHERE c.id = :idTeam
            """, nativeQuery = true)
    List<StTemplateMemberFactoryResponse> getMemberTeamFactory(@Param("idTeam") String idTeam);
}
