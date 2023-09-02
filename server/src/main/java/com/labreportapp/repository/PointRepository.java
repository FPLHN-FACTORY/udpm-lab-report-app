package com.labreportapp.repository;

import com.labreportapp.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository
public interface PointRepository extends JpaRepository<Point, String> {
}
