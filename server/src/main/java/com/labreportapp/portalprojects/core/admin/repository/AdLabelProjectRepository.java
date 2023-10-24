package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.entity.LabelProject;
import com.labreportapp.portalprojects.repository.LabelProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author NguyenVinh
 */
@Repository
public interface AdLabelProjectRepository extends LabelProjectRepository {

    List<LabelProject> findAllByProjectId(String idProject);
}
