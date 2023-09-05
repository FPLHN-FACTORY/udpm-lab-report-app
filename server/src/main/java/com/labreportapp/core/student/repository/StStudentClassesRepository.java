package com.labreportapp.core.student.repository;

import com.labreportapp.entity.StudentClasses;
import com.labreportapp.repository.StudentClassesRepository;

import java.util.Optional;

/**
 * @author thangncph26123
 */
public interface StStudentClassesRepository extends StudentClassesRepository {

    Optional<StudentClasses> findStudentClassesByClassIdAndStudentId(String classId, String studentId);
}
