package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.LabelProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(LabelProjectRepository.NAME)
public interface LabelProjectRepository extends JpaRepository<LabelProject, String> {

    public static final String NAME = "BaseLabelProjectRepository";

}
