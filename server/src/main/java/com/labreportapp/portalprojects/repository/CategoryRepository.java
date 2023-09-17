package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository(CategoryRepository.NAME)
public interface CategoryRepository extends JpaRepository<Category, String> {

    public static final String NAME = "BaseCategoryRepository";

    @Query(value = """
            SELECT id, name FROM category
            """, nativeQuery = true)
    List<SimpleEntityProj> findAllSimpleEntity();
}
