package com.od.notesmaker.service;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.UserNotFoundException;
import com.od.notesmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User getUserByLogin(String login) throws UserNotFoundException {
        return Optional
                .ofNullable(userRepository.getUserByLogin(login))
                .orElseThrow(() -> new UserNotFoundException("User with given login=\"" + login + "\" not found"));
    }

    @Override
    public void updateUserDetails(Long userId, User userDetails) throws UserNotFoundException {
        User oldUser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with given ID=\"" + userId + "\" not found"));

        oldUser.setName(userDetails.getName());
        oldUser.setSurname(userDetails.getSurname());
        oldUser.setEmail(userDetails.getEmail());
        oldUser.setBirthDate(userDetails.getBirthDate());

        userRepository.save(oldUser);
    }

    @Override
    public void updateUserPassword(Long userId, String newPassword) throws UserNotFoundException {
        User oldUser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with given ID=\"" + userId + "\" not found"));

        oldUser.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(oldUser);
    }

    @Override
    public boolean userLoginExists(String login) {
        return userRepository.getUserByLogin(login) != null ? true : false;
    }

    @Override
    public boolean userEmailExists(String email) {
        return userRepository.getUserByEmail(email) != null ? true : false;
    }
}
