package com.od.notesmaker.service;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.LoginAttemptException;
import com.od.notesmaker.exception.UserNotFoundException;
import com.od.notesmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String username = login;
        if (loginAttemptService.isBlocked(username)) {
            throw new LoginAttemptException("User with login \"" + username + "\" was blocked!");
        }

        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            User user = userService.getUserByLogin(login);

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            System.out.println(user);
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User with given login was not found");
        }
    }

}
