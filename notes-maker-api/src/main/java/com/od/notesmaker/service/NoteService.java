package com.od.notesmaker.service;

import com.od.notesmaker.entity.Note;
import com.od.notesmaker.exception.NoteNotFoundException;
import com.od.notesmaker.exception.UserNotFoundException;

import java.util.List;

public interface NoteService {

    void addNote(Note note, Long userId) throws UserNotFoundException;

    List<Note> getAllNotes();

    Note getNoteById(Long noteId);

    void updateNote(Long noteId, Note noteDetails) throws NoteNotFoundException;

    void deleteNote(Long noteId) throws NoteNotFoundException;

}
