package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.GroupProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository(GroupProjectRepository.NAME)
public interface GroupProjectRepository extends JpaRepository<GroupProject, String> {

    String NAME = "BaseGroupProjectRepository";
}
