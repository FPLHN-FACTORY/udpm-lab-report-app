package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.ActivityTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ActivityRepository.NAME)
public interface ActivityRepository extends JpaRepository<ActivityTodo, String> {

    public static final String NAME = "BaseActivityTodoRepository";
}
