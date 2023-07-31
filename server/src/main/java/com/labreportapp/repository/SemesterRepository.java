package com.labreportapp.repository;

import com.labreportapp.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(SemesterRepository.NAME)
public interface SemesterRepository extends JpaRepository<Semester, String> {

    String NAME = "BaseSemesterRepository";
}
