package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.LabelProjectTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(LabelProjectTodoRepository.NAME)
public interface LabelProjectTodoRepository extends JpaRepository<LabelProjectTodo, String> {

    public static final String NAME = "BaseLabelTodoRepository";
}
