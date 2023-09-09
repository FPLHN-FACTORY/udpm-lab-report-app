package com.labreportapp.core.teacher.repository;

import com.labreportapp.core.teacher.model.response.TePointRespone;
import com.labreportapp.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TePointRepository extends JpaRepository<Point, String> {

    List<TePointRespone> getAllPointByIdClass();
}
