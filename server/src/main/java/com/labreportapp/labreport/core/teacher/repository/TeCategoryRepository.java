package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.portalprojects.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894 - duchieu212
 */
@Repository
public interface TeCategoryRepository extends JpaRepository<Category, String> {
}
