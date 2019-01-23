package com.od.notesmaker.service;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.UserNotFoundException;
import com.od.notesmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserByLogin(String login) throws UserNotFoundException {
        User user = userRepository.getUserByLogin(login);

        if (user == null) {
            throw new UserNotFoundException("User with given login=\"" + login + "\" not found");
        } else {
            return userRepository.getUserByLogin(login);
        }
    }

    @Override
    public void updateUser(Long userId, User userDetails) throws UserNotFoundException {
        User oldUser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with given ID=\"" + userId + "\" not found"));

        oldUser.setName(userDetails.getName());
        oldUser.setSurname(userDetails.getSurname());
        oldUser.setEmail(userDetails.getEmail());
        oldUser.setBirthDate(userDetails.getBirthDate());

        userRepository.save(oldUser);
    }

    @Override
    public void updateUserPassword(Long userId, String newPassword) {
        User oldUser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with given ID=\"" + userId + "\" not found"));

        oldUser.setPassword(newPassword);

        userRepository.save(oldUser);
    }
}
