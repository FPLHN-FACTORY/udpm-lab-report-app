package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.entity.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author quynhncph26201
 */
public interface StHomeWorkRepository extends JpaRepository<HomeWork, String> {
}