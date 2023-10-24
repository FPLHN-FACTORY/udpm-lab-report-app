package com.labreportapp.portalprojects.core.admin.repository;


import com.labreportapp.portalprojects.core.admin.model.response.AdProjectCategoryReponse;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import com.labreportapp.portalprojects.repository.ProjectCategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hieundph25894
 */
@Repository
public interface AdProjectCategoryRepository extends ProjectCategoryRepository {

    @Query("SELECT projectCategory.id as id, projectCategory.projectId as projectId, category.id as categoryId, category.code as categoryCode," +
            " category.name as categoryName FROM ProjectCategory projectCategory " +
            "Join Category category on category.id = projectCategory.categoryId " +
            " WHERE projectCategory.projectId = :id")
    List<AdProjectCategoryReponse> getAllByIdProject(@Param("id") String idProject);

    List<ProjectCategory> findAllByProjectId(String idProject);

}
