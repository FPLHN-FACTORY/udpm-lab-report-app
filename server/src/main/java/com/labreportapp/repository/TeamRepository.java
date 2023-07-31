package com.labreportapp.repository;

import com.labreportapp.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(TeamRepository.NAME)
public interface TeamRepository extends JpaRepository<Team, String> {

    String NAME = "BaseTeamRepository";
}
