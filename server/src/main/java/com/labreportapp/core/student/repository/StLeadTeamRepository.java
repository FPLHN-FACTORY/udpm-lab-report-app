package com.labreportapp.core.student.repository;


import com.labreportapp.entity.StudentClasses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author quynhncph26201
 */
public interface StLeadTeamRepository extends JpaRepository<StudentClasses, String> {

    Optional<StudentClasses> findStudentClassesByStudentId(String idStudent);
}
