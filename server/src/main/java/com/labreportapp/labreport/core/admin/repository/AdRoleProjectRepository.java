package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindRoleProjectRequest;
import com.labreportapp.labreport.core.admin.model.response.AdRoleProjectResponse;
import com.labreportapp.portalprojects.entity.RoleConfig;
import com.labreportapp.portalprojects.entity.RoleProject;
import com.labreportapp.portalprojects.repository.RoleConfigRepository;
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
public interface AdRoleProjectRepository extends RoleConfigRepository {

    @Query(" SELECT obj FROM RoleConfig obj")
    List<RoleConfig> getAllRoleProject(Pageable pageable);

    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.created_date DESC ) AS stt ,
            obj.id,
            obj.name as name,
            obj.description as description,
            obj.role_default
            FROM role_config obj 
            WHERE  ( :#{#req.name} IS NULL 
                   OR :#{#req.name} LIKE '' 
                   OR obj.name LIKE %:#{#req.name}% )
                    ORDER BY obj.created_date DESC         
                    """, countQuery = """    
            SELECT COUNT(obj.id) 
            FROM role_config obj 
            WHERE ( :#{#req.name} IS NULL 
                    OR :#{#req.name} LIKE '' 
                    OR obj.name LIKE %:#{#req.name}% )     
                    ORDER BY obj.created_date DESC       
            """, nativeQuery = true)
    Page<AdRoleProjectResponse> searchRoleProject(@Param("req") AdFindRoleProjectRequest req, Pageable page);

    @Query(value = """
            SELECT id FROM role_config WHERE role_default = 0
            """, nativeQuery = true)
    String getRoleConfigDefault();

}
