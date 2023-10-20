package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindRoleFactoryRequest;
import com.labreportapp.labreport.core.admin.model.response.AdRoleFactoryResponse;
import com.labreportapp.labreport.entity.RoleFactory;
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
public interface AdRoleFactoryRepository extends JpaRepository<RoleFactory, String> {

    @Query(" SELECT obj FROM RoleFactory obj")
    List<RoleFactory> getAllRoleFactory(Pageable pageable);

    @Query(value = """
            SELECT 
            ROW_NUMBER() OVER(ORDER BY obj.created_date DESC ) AS stt,
                obj.id as id,
                obj.name as name,
                obj.descriptions as descriptions,
                obj.role_default
            FROM role_factory obj 
            WHERE  ( :#{#req.name} IS NULL 
                   OR :#{#req.name} LIKE '' 
                   OR obj.name LIKE %:#{#req.name}% )
                    ORDER BY obj.created_date DESC         
                    """, countQuery = """    
            SELECT COUNT(obj.id) 
            FROM role_factory obj 
            WHERE ( :#{#req.name} IS NULL 
                    OR :#{#req.name} LIKE '' 
                    OR obj.name LIKE %:#{#req.name}% )     
                    ORDER BY obj.created_date DESC       
            """, nativeQuery = true)
    Page<AdRoleFactoryResponse> searchRoleFactory(@Param("req") AdFindRoleFactoryRequest req, Pageable page);

    @Query(value = "SELECT COUNT(*) FROM member_factory a join role_factory b on a.role_factory_id=b.id WHERE b.id = :id", nativeQuery = true)
    Integer countMemberFactoryByRoleId(@Param("id") String id);

    @Query(value = """
            SELECT id FROM role_factory WHERE role_default = 0
            """, nativeQuery = true)
    String getRoleConfigDefault();
}
