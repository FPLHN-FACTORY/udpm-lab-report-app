package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.MemberRoleFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(MemberRoleFactoryRepository.NAME)
public interface MemberRoleFactoryRepository extends JpaRepository<MemberRoleFactory, String> {

    String NAME = "BaseMemberRoleFactoryRepository";
}
