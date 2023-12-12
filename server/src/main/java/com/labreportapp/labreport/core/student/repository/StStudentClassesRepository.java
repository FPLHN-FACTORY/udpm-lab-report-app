package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.repository.StudentClassesRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author thangncph26123
 */
@Repository
public interface StStudentClassesRepository extends StudentClassesRepository {

    Optional<StudentClasses> findStudentClassesByClassIdAndStudentId(String classId, String studentId);
}
