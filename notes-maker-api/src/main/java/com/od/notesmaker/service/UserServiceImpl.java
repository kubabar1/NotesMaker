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
    public User getUserByLogin(String login) throws UserNotFoundException{
        User user = userRepository.getUserByLogin(login);

        if(user==null){
            throw new UserNotFoundException("User with given login=\"" + login + "\" not found");
        }else{
            return userRepository.getUserByLogin(login);
        }
    }
}
