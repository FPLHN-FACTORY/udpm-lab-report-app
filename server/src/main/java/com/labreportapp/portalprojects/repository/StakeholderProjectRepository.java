package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.StakeholderProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(StakeholderProjectRepository.NAME)
public interface StakeholderProjectRepository extends JpaRepository<StakeholderProject, String> {

    public static final String NAME = "BaseStakeholderProjectRepository";


}
