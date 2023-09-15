package com.labreportapp.core.student.repository;

import com.labreportapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author quynhncph26201
 */
public interface StNoteRepository extends JpaRepository<Note, String> {
}
