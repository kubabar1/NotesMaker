package com.od.notesmaker.exception;

public class LoginAttemptException extends RuntimeException {
    public LoginAttemptException() {
        super();
    }

    public LoginAttemptException(String message) {
        super(message);
    }

    public LoginAttemptException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAttemptException(Throwable cause) {
        super(cause);
    }
}
