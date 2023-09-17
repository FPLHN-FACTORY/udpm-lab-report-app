package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.response.AdProjectStkResponse;
import com.labreportapp.portalprojects.entity.StakeholderProject;
import com.labreportapp.portalprojects.repository.StakeholderProjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Repository
public interface AdStakeholderProjectRepository extends StakeholderProjectRepository {
    @Query(value = """ 
            SELECT p.* FROM project p 
            JOIN stakeholder_project sp 
            ON p.id = sp.project_id 
            WHERE sp.stakeholder_id = :stakeholderId """, nativeQuery = true)
    List<AdProjectStkResponse> findByStakeholderId(@Param("stakeholderId") String stakeholderId);

    @Query(value = """ 
            SELECT *
            FROM project""", nativeQuery = true)
    List<AdProjectStkResponse> getAllProject();

    @Query(value = """
            SELECT * FROM stakeholder_project
            WHERE stakeholder_id = :stakeholderId and project_id = :projectId """, nativeQuery = true)
    StakeholderProject findStakeProjet(@Param("stakeholderId") String stakeholderId, @Param("projectId") String projectId);


}


