package com.od.notesmaker.controller;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.UserNotFoundException;
import com.od.notesmaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/current-user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Boolean> isAuthenticated() {
        Boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        return new ResponseEntity<>(isAuthenticated, HttpStatus.OK);
    }

    @GetMapping(value = "/data")
    public ResponseEntity<Map<String, String>> getCurrentUserData(Authentication authentication) {
        User user = userService.getUserByLogin(authentication.getName());

        Map<String, String> userData = new HashMap<>();
        userData.put("id", user.getId().toString());
        userData.put("login", user.getLogin());
        userData.put("name", user.getName());
        userData.put("surname", user.getSurname());
        userData.put("email", user.getEmail());
        userData.put("birthdate", user.getBirthDate().toString());

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @PutMapping(value = "/update-data")
    public ResponseEntity updateCurrentUserData(@Valid User userUpdate, Authentication authentication) {
        try {
            User currentUser = userService.getUserByLogin(authentication.getName());
            userService.updateUser(currentUser.getId(), userUpdate);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update-password")
    public ResponseEntity updateCurrentUserPassword(@RequestParam String currentPassword, @RequestParam String newPassword,
                                                    Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            if(newPassword.length()<8){
                return new ResponseEntity<>("Password cannot be shorter than 8 chars",HttpStatus.BAD_REQUEST);
            }else{
                userService.updateUserPassword(currentUser.getId(), passwordEncoder.encode(newPassword));
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity("Wrong current password", HttpStatus.BAD_REQUEST);
        }
    }

}
