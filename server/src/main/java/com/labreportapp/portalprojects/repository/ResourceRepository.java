package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ResourceRepository.NAME)
public interface ResourceRepository extends JpaRepository<Resource, String> {

    public static final String NAME = "BaseResourceRepository";
}
