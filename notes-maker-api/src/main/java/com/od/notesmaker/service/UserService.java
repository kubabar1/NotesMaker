package com.od.notesmaker.service;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.UserNotFoundException;

public interface UserService {

    User getUserByLogin(String login) throws UserNotFoundException;

    void updateUserDetails(Long userId, User userDetails) throws UserNotFoundException;

    void updateUserPassword(Long userId, String newPassword) throws UserNotFoundException;
}
