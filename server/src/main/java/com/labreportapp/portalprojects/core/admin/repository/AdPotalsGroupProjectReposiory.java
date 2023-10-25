package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.entity.GroupProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface AdPotalsGroupProjectReposiory extends JpaRepository<GroupProject, String> {
}
