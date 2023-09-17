package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository
public interface PointRepository extends JpaRepository<Point, String> {
}
