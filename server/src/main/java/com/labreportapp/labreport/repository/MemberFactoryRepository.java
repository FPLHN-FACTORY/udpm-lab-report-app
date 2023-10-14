package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.MemberFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(MemberFactoryRepository.NAME)
public interface MemberFactoryRepository extends JpaRepository<MemberFactory, String> {

    String NAME = "BaseMemberFactoryRepository";
}
