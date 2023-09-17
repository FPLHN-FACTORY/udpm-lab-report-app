package com.labreportapp.labreport.core.student.repository;

import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.repository.StudentClassesRepository;

import java.util.Optional;

/**
 * @author thangncph26123
 */
public interface StStudentClassesRepository extends StudentClassesRepository {

    Optional<StudentClasses> findStudentClassesByClassIdAndStudentId(String classId, String studentId);
}
