package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.labreport.entity.MemberFactory;
import com.labreportapp.labreport.repository.MemberFactoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface AdPotalsMemberFactoryRepository extends MemberFactoryRepository {

    @Query(value = """
            SELECT * FROM member_factory mf
            WHERE mf.status_member_factory = '0'
             ORDER BY mf.created_date DESC
             """, nativeQuery = true)
    List<MemberFactory> getAllMemberFactory();
}
