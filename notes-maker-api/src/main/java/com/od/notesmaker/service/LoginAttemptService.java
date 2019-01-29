package com.od.notesmaker.service;

public interface LoginAttemptService {

    void loginSucceeded(String login);

    void loginFailed(String login);

    boolean isBlocked(String login);

}
