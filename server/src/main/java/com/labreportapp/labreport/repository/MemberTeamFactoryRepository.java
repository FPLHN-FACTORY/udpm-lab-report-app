package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.MemberTeamFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(MemberTeamFactoryRepository.NAME)
public interface MemberTeamFactoryRepository extends JpaRepository<MemberTeamFactory, String> {

    String NAME = "BaseMemberTeamFactoryRepository";
}
