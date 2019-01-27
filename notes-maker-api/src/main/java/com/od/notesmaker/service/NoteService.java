package com.od.notesmaker.service;

import com.od.notesmaker.entity.Note;
import com.od.notesmaker.exception.NoteNotFoundException;
import com.od.notesmaker.exception.UserNotFoundException;

import java.util.List;

public interface NoteService {

    void addNote(Note note, Long userId) throws UserNotFoundException;

    List<Note> getUserNotes(Long userId);

    List<Note> getAllNotes();

    List<Note> getAllPublishedNotes();

    Note getNoteById(Long noteId) throws NoteNotFoundException;

    void updateNote(Long noteId, Note noteDetails) throws NoteNotFoundException;

    void deleteNote(Long noteId) throws NoteNotFoundException;
}
