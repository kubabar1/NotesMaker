package com.od.notesmaker.service;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.UserNotFoundException;

public interface UserService {

    User getUserByLogin(String login);

    void updateUser(Long userId, User userDetails) throws UserNotFoundException;

    void updateUserPassword(Long id, String encode);
}
