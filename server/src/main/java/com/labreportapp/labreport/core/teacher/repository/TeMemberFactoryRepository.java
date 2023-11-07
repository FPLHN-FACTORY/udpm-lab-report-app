package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMemberFactoryRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMemberFactoryResponse;
import com.labreportapp.labreport.entity.MemberFactory;
import com.labreportapp.labreport.repository.MemberFactoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface TeMemberFactoryRepository extends MemberFactoryRepository {

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
    Page<TeMemberFactoryResponse> getAllRoleProject(Pageable pageable, @Param("req") TeFindMemberFactoryRequest req);

    @Query(value = """
            SELECT a.id, a.name FROM role_factory a ORDER BY a.created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getRoles();

    @Query(value = """
            SELECT DISTINCT COUNT(*) FROM member_factory
            """, nativeQuery = true)
    Integer getNumberMemberFactory();

    @Query(value = """
            SELECT * FROM member_factory ORDER BY created_date DESC
            """, nativeQuery = true)
    List<MemberFactory> getAllMemberFactory();

}
