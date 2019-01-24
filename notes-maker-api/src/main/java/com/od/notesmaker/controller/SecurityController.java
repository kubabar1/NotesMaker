package com.od.notesmaker.controller;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/current-user")
public class SecurityController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Boolean> isAuthenticated() {
        Boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        return new ResponseEntity<>(isAuthenticated, HttpStatus.OK);
    }

    @GetMapping(value = "/data")
    public ResponseEntity<Map<String,String>> getCurrentUserData(){
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        Map<String,String> userData = new HashMap<>();
        userData.put("id",user.getId().toString());
        userData.put("login",user.getLogin());
        userData.put("name",user.getName());
        userData.put("surname",user.getSurname());
        userData.put("email",user.getEmail());
        userData.put("birthdate",user.getBirthDate().toString());

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

}
