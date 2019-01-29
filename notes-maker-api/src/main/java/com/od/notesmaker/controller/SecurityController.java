package com.od.notesmaker.controller;

import com.od.notesmaker.service.LastLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/security")
public class SecurityController {

    @Autowired
    private LastLoginService lastLoginService;

    @GetMapping(value = "/last-logins")
    public ResponseEntity<List<String>> getLastLoginList(HttpServletRequest request, Authentication authentication) {
        List<String> lastLoginList = lastLoginService.getAllLastLogins(authentication.getName());

        if (lastLoginList.isEmpty()) {
            return new ResponseEntity<>(lastLoginList, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lastLoginList, HttpStatus.OK);
        }
    }


}
