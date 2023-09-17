package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hieundph25894
 */
public interface TeNoteRepository extends JpaRepository<Note, String> {
}
