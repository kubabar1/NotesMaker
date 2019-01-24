package com.od.notesmaker.error;

import com.od.notesmaker.exception.LoginAttemptException;
import com.od.notesmaker.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({LoginAttemptException.class})
    public ResponseEntity<Object> handleLoginAttemptException(Exception ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}
