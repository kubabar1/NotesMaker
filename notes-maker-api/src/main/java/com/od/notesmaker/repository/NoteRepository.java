package com.od.notesmaker.repository;

import com.od.notesmaker.entity.Note;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
}
