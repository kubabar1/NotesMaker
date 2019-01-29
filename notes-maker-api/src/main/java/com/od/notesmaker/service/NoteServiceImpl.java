package com.od.notesmaker.service;

import com.od.notesmaker.entity.Note;
import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.NoteNotFoundException;
import com.od.notesmaker.exception.UserNotFoundException;
import com.od.notesmaker.repository.NoteRepository;
import com.od.notesmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addNote(Note note, Long userId) throws UserNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with given ID=\"" + userId + "\" not found"));

        note.setUser(user);
        note.setCreationDate(new Timestamp(new Date().getTime()));

        noteRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes() {
        return (List<Note>) noteRepository.findAll();
    }

    @Override
    public Note getNoteById(Long noteId) throws NoteNotFoundException {
        return noteRepository.findById(noteId).orElseThrow(() ->
                new NoteNotFoundException("Note with given ID=\"" + noteId + "\" not found"));
    }

    @Override
    public void updateNote(Long noteId, Note noteUpdate) throws NoteNotFoundException {
        Note oldNote = noteRepository.findById(noteId).orElseThrow(() ->
                new NoteNotFoundException("Note with given ID=\"" + noteId + "\" not found"));

        oldNote.setName(noteUpdate.getName());
        oldNote.setContent(noteUpdate.getContent());

        noteRepository.save(oldNote);
    }

    @Override
    public void deleteNote(Long noteId) throws NoteNotFoundException {
        noteRepository.findById(noteId).orElseThrow(() ->
                new NoteNotFoundException("Note with given ID=\"" + noteId + "\" not found"));

        noteRepository.deleteById(noteId);
    }

    @Override
    public List<Note> getUserNotes(Long userId) {
        return noteRepository.getUserNotesByUserId(userId);
    }

    @Override
    public List<Note> getAllPublishedNotes() {
        return noteRepository.getAllPublishedNotes();
    }
}
