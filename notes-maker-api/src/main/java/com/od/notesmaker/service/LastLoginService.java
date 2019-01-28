package com.od.notesmaker.service;

import java.util.List;

public interface LastLoginService {

    void addLastLogin(String ip, String login);

    List<String> getAllLastLogins(String login);

}
