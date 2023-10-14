package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.TeamFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(TeamFactoryRepository.NAME)
public interface TeamFactoryRepository extends JpaRepository<TeamFactory, String> {

    String NAME = "BaseTeamFactoryRepostitory";
}
