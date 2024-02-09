package dev.notetoself.NoteToSelfSpring.repository;

import dev.notetoself.NoteToSelfSpring.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByNoteContentContainsIgnoreCase(String noteContent);
}
