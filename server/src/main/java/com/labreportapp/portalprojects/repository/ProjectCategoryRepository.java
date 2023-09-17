package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.ProjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ProjectCategoryRepository.NAME)
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, String> {

    public static final String NAME = "BaseProjectCategoryRepository";
}
