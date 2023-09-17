package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.StudentClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(StudentClassesRepository.NAME)
public interface StudentClassesRepository extends JpaRepository<StudentClasses, String> {

    String NAME = "BaseStudentClassesRepository";
}
