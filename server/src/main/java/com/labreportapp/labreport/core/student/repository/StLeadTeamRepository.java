package com.labreportapp.labreport.core.student.repository;


import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.repository.StudentClassesRepository;

import java.util.Optional;

/**
 * @author quynhncph26201
 */
public interface StLeadTeamRepository extends StudentClassesRepository {

    Optional<StudentClasses> findStudentClassesByStudentIdAndClassId(String idStudent, String classId);
}
