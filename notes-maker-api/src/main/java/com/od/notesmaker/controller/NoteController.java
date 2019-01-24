package com.od.notesmaker.controller;

import com.od.notesmaker.entity.Note;
import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.NoteNotFoundException;
import com.od.notesmaker.exception.UserNotFoundException;
import com.od.notesmaker.service.NoteService;
import com.od.notesmaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Note>> getCurrentUserNotes(Authentication authentication) {
        try {
            User user = userService.getUserByLogin(authentication.getName());
            List<Note> userNotes = noteService.getUserNotes(user.getId());

            if (userNotes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(userNotes, HttpStatus.OK);
            }
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/published/all")
    public ResponseEntity<List<Note>> getAllPublishedNotes() {
        List<Note> allNotes = noteService.getAllPublishedNotes();

        if (allNotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(allNotes, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{noteId}")
    public ResponseEntity<Note> getUserNote(@PathVariable Long noteId, Authentication authentication) {
        Note note = noteService.getNoteById(noteId);
        String userLogin = authentication.getName();

        List<Note> allNotes = noteService.getAllPublishedNotes();

        if (allNotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            if (note.getUser().getLogin().equals(userLogin) || note.getPublished()) {
                return new ResponseEntity<>(note, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PostMapping
    public ResponseEntity addNote(@Valid Note note, Authentication authentication) {
        User user = userService.getUserByLogin(authentication.getName());

        try {
            noteService.addNote(note, user.getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity deleteNote(@PathVariable Long noteId, Authentication authentication) {
        Note note = noteService.getNoteById(noteId);
        String userLogin = authentication.getName();

        if (note.getUser().getLogin().equals(userLogin)) {
            try {
                noteService.deleteNote(noteId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoteNotFoundException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/{noteId}")
    public ResponseEntity updateNote(@Valid Note noteUpdate, @PathVariable Long noteId, Authentication authentication) {
        Note note = noteService.getNoteById(noteId);
        String userLogin = authentication.getName();

        if (note.getUser().getLogin().equals(userLogin)) {
            try {
                noteService.updateNote(noteId, noteUpdate);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoteNotFoundException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/publish/{noteId}")
    public ResponseEntity publishNote(@PathVariable Long noteId, Authentication authentication) {
        Note note = noteService.getNoteById(noteId);
        String userLogin = authentication.getName();

        if (note.getUser().getLogin().equals(userLogin)) {
            try {
                note.setPublished(true);
                noteService.updateNote(noteId, note);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoteNotFoundException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/unpublish/{noteId}")
    public ResponseEntity unpublishNote(@PathVariable Long noteId, Authentication authentication) {
        Note note = noteService.getNoteById(noteId);
        String userLogin = authentication.getName();

        if (note.getUser().getLogin().equals(userLogin)) {
            try {
                note.setPublished(false);
                noteService.updateNote(noteId, note);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoteNotFoundException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> allNotes = noteService.getAllNotes();

        if (allNotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(allNotes, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/all/{noteId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId) {
        try {
            Note note = noteService.getNoteById(noteId);
            return new ResponseEntity<>(note, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/all/{noteId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity updateNote(@Valid Note note, @PathVariable Long noteId) {
        try {
            noteService.updateNote(noteId, note);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/all/{noteId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteNote(@PathVariable Long noteId) {
        try {
            noteService.deleteNote(noteId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
