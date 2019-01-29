package com.od.notesmaker.config;

import com.od.notesmaker.service.LoginAttemptService;
import com.od.notesmaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String login = e.getAuthentication().getName();

        if (userService.userLoginExists(login)) {
            loginAttemptService.loginFailed(login);
        }
    }
}
