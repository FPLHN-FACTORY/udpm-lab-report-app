package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(NoteRepository.NAME)
public interface NoteRepository extends JpaRepository<Note, String> {

    String NAME = "BaseNoteRepository";
}
