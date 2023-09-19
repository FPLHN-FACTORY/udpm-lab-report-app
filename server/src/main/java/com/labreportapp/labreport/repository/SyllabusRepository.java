package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(SyllabusRepository.NAME)
public interface SyllabusRepository extends JpaRepository<Syllabus, String> {

    String NAME = "BaseSyllabusRepository";
}
