package com.od.notesmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NotesmakerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NotesmakerApplication.class, args);
    }

}

