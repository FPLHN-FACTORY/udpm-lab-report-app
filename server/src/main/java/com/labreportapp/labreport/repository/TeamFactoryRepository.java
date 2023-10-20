package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.entity.TeamFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository(TeamFactoryRepository.NAME)
public interface TeamFactoryRepository extends JpaRepository<TeamFactory, String> {

    String NAME = "BaseTeamFactoryRepostitory";

    @Query(value = """
            SELECT a.id, a.name FROM team_factory a ORDER BY a.created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getTeams();
}
