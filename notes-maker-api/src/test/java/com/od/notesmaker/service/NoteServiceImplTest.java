package com.od.notesmaker.service;

import com.od.notesmaker.entity.Note;
import com.od.notesmaker.entity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NoteServiceImplTest {

    @Autowired
    private NoteService noteService;

    private static Note note;

    @BeforeClass
    public static void beforeClass() throws ParseException {
        String creationDate = "2019-01-05 12:12:12";
        SimpleDateFormat sdf1X = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date dateX = sdf1X.parse(creationDate);
        java.sql.Timestamp sqlCreationDate = new java.sql.Timestamp(dateX.getTime());

        note = new Note("Test 1", "Zażółć gęślą jaźń");
        note.setPublished(false);
        note.setId((long) 1);
        note.setCreationDate(sqlCreationDate);

        String birthdayDate = "2012-12-11";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(birthdayDate);
        java.sql.Date sqlBirthdayDate = new java.sql.Date(date.getTime());
        User jan123 = new User("Jan", "Kowalski", "jan123",
                "$2a$10$FabGp5zLvInm/DAcHtmRc.ws1.tQK7eXamK/mCj/BPCSlB5yDyNH2",
                "jan123@test.com", sqlBirthdayDate);
        jan123.setId((long) 1);

        note.setUser(jan123);
    }

    @Test
    public void addNote() {
        Note newNote = new Note("qwerty", "asdfgh");
        noteService.addNote(newNote, (long) 1);
        assertEquals(noteService.getAllNotes().size(), 3);
    }

    @Test
    public void getAllNotes() {
        List<Note> noteList = noteService.getAllNotes();

        assertEquals(note.getUser(), noteList.get(0).getUser());
        assertEquals(note.getId(), noteList.get(0).getId());
        assertEquals(note.getName(), noteList.get(0).getName());
        assertEquals(note.getContent(), noteList.get(0).getContent());
        assertTrue(note.getCreationDate().getTime() <= System.currentTimeMillis());
        assertEquals(note.getPublished(), noteList.get(0).getPublished());
        assertEquals(noteList.size(), 2);
    }

    @Test
    public void getNoteById() {
        assertEquals(note, noteService.getNoteById((long) 1));
    }

    @Test
    public void updateNote() {
        Note newNote = noteService.getNoteById((long) 1);
        newNote.setName("adg");
        newNote.setContent("zxc");

        noteService.updateNote((long) 1, newNote);
        assertEquals(noteService.getNoteById((long) 1), newNote);
    }

    @Test
    public void deleteNote() {
        noteService.deleteNote((long) 1);
        assertEquals(1, noteService.getAllNotes().size());
    }

    @Test
    public void getUserNotes() {
        List<Note> userNotes = noteService.getUserNotes((long) 1);
        assertEquals(note, userNotes.get(0));
        assertEquals(1, userNotes.size());
    }

    @Test
    public void getAllPublishedNotes() {
        List<Note> publishedNotes = noteService.getAllPublishedNotes();
        assertEquals(true, publishedNotes.get(0).getPublished());
        assertEquals(1, publishedNotes.size());
    }
}