package com.od.notesmaker.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException() {
        super();
    }

    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteNotFoundException(Throwable cause) {
        super(cause);
    }
}
