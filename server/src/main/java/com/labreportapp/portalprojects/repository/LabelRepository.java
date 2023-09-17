package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Label;
import com.labreportapp.portalprojects.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository(LabelRepository.NAME)
public interface LabelRepository extends JpaRepository<Label, String> {

    public static final String NAME = "BaseLabelRepository";

    @Query(value = """
            SELECT id, name FROM label
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();
}
