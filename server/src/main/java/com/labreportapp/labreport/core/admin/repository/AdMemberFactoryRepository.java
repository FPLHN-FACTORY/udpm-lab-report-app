package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.request.AdFindMemberFactoryRequest;
import com.labreportapp.labreport.core.admin.model.response.AdMemberFactoryResponse;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.repository.MemberFactoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdMemberFactoryRepository extends MemberFactoryRepository {

    @Query(value = """
            WITH memberFactoryRole AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS role_member_factory
                FROM member_factory a
                LEFT JOIN member_role_factory b ON a.id = b.member_factory_id
                LEFT JOIN role_factory c ON c.id = b.role_factory_id
                GROUP BY a.id
            ), memberFactoryNumberTeam AS (
                SELECT a.id, COUNT(c.id) AS number_team
                FROM member_factory a
                LEFT JOIN member_team_factory b ON a.id = b.member_factory_id
                LEFT JOIN team_factory c ON c.id = b.team_factory_id
                GROUP BY a.id
            )
            SELECT DISTINCT a.id, ROW_NUMBER() OVER(ORDER BY a.created_date DESC) AS stt, a.member_id, a.email, a.status_member_factory, mfr.role_member_factory, mfnt.number_team
            FROM member_factory a
            LEFT JOIN member_role_factory b ON a.id = b.member_factory_id
            LEFT JOIN memberFactoryRole mfr ON mfr.id = a.id
            LEFT JOIN memberFactoryNumberTeam mfnt ON mfnt.id = a.id
            WHERE (:#{#req.value} IS NULL OR :#{#req.value} LIKE '' OR a.email LIKE %:#{#req.value}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_member_factory = :#{#req.status})
            AND (:#{#req.roleFactoryId} IS NULL OR :#{#req.roleFactoryId} LIKE '' OR b.role_factory_id = :#{#req.roleFactoryId})
            GROUP BY a.id
            ORDER BY a.created_date DESC
            """, countQuery = """
            WITH memberFactoryRole AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS role_member_factory
                FROM member_factory a
                LEFT JOIN member_role_factory b ON a.id = b.member_factory_id
                LEFT JOIN role_factory c ON c.id = b.role_factory_id
                GROUP BY a.id
            ), memberFactoryNumberTeam AS (
                SELECT a.id, COUNT(c.id) AS number_team
                FROM member_factory a
                LEFT JOIN member_team_factory b ON a.id = b.member_factory_id
                LEFT JOIN team_factory c ON c.id = b.team_factory_id
                GROUP BY a.id
            )
            SELECT COUNT(DISTINCT a.id)
            FROM member_factory a
            LEFT JOIN member_role_factory b ON a.id = b.member_factory_id
            LEFT JOIN memberFactoryRole mfr ON mfr.id = a.id
            LEFT JOIN memberFactoryNumberTeam mfnt ON mfnt.id = a.id
            WHERE (:#{#req.value} IS NULL OR :#{#req.value} LIKE '' OR a.email LIKE %:#{#req.value}%)
            AND (:#{#req.status} IS NULL OR :#{#req.status} LIKE '' OR a.status_member_factory = :#{#req.status})
            AND (:#{#req.roleFactoryId} IS NULL OR :#{#req.roleFactoryId} LIKE '' OR b.role_factory_id = :#{#req.roleFactoryId})
            GROUP BY a.id
            ORDER BY a.created_date DESC
            """, nativeQuery = true)
    Page<AdMemberFactoryResponse> getAllRoleProject(Pageable pageable, @Param("req") AdFindMemberFactoryRequest req);

    @Query(value = """
            SELECT a.id, a.name FROM role_factory a ORDER BY a.created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getRoles();

    @Query(value = """
            SELECT DISTINCT COUNT(*) FROM member_factory
            """, nativeQuery = true)
    Integer getNumberMemberFactory();

    @Query(value = """
            SELECT a.id FROM member_factory a WHERE a.email = :email
            """, nativeQuery = true)
    String getMemberFactoryByEmail(@Param("email") String email);

    @Query(value = """
            SELECT DISTINCT c.id FROM member_factory a 
            JOIN member_role_factory b ON a.id = b.member_factory_id
            JOIN role_factory c ON c.id = b.role_factory_id
            WHERE a.id = :id
            """, nativeQuery = true)
    List<String> getRolesMemberFactory(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM member_role_factory WHERE member_factory_id = :id
            """, nativeQuery = true)
    Integer deleteRolesMemberFactory(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM member_team_factory WHERE member_factory_id = :id
            """, nativeQuery = true)
    Integer deleteTeamsMemberFactory(@Param("id") String id);

    @Query(value = """
            SELECT GROUP_CONCAT(c.name, ' ') AS role_member_factory
            FROM member_factory a
            LEFT JOIN member_role_factory b ON a.id = b.member_factory_id
            LEFT JOIN role_factory c ON c.id = b.role_factory_id
            WHERE a.id = :id
            GROUP BY a.id
            """, nativeQuery = true)
    String getNameRolesMemberFactory(@Param("id") String id);

    @Query(value = """
            SELECT DISTINCT c.id FROM member_factory a 
            JOIN member_team_factory b ON a.id = b.member_factory_id
            JOIN team_factory c ON c.id = b.team_factory_id
            WHERE a.id = :id
            """, nativeQuery = true)
    List<String> getTeamsMemberFactory(@Param("id") String id);
}
