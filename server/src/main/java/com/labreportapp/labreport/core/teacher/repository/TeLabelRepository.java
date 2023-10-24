package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.portalprojects.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hieundph25894
 */
@Repository
public interface TeLabelRepository extends JpaRepository<Label, String> {
}
