package com.od.notesmaker.repository;

import com.od.notesmaker.entity.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {

    List<Note> getUserNotesByUserId(Long userId);

    @Query("SELECT n FROM Note n WHERE n.published=TRUE")
    List<Note> getAllPublishedNotes();
}
