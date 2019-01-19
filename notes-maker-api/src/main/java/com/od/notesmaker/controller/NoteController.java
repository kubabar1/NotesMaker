package com.od.notesmaker.controller;

import com.od.notesmaker.entity.Note;
import com.od.notesmaker.exception.NoteNotFoundException;
import com.od.notesmaker.exception.UserNotFoundException;
import com.od.notesmaker.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity addNote(@Valid Note note, @RequestParam Long userId) {
        try {
            noteService.addNote(note, userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes() {
        List<Note> allNotes = noteService.getAllNotes();

        if (allNotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(allNotes, HttpStatus.OK);
        }
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId) {
        try {
            Note note = noteService.getNoteById(noteId);
            return new ResponseEntity<>(note, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{noteId}")
    public ResponseEntity updateNote(@Valid Note note, @PathVariable Long noteId) {
        try {
            noteService.updateNote(noteId, note);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity deleteNote(@PathVariable Long noteId) {
        try {
            noteService.deleteNote(noteId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
