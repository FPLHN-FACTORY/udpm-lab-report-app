package com.labreportapp.core.teacher.repository;

import com.labreportapp.entity.HomeWork;
import com.labreportapp.entity.Note;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hieundph25894
 */
public interface TeNoteRepository extends JpaRepository<Note, String> {
}
