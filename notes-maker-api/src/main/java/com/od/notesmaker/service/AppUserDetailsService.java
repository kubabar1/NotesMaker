package com.od.notesmaker.service;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.LoginAttemptException;
import com.od.notesmaker.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private LastLoginService lastLoginService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, LoginAttemptException {
        String ip = getClientIP();
        System.out.println(ip);

        if (loginAttemptService.isBlocked(login)) {
            throw new LoginAttemptException("User with login \"" + login + "\" was blocked!");
        }


        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            User user = userService.getUserByLogin(login);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            lastLoginService.addLastLogin(getClientIP(), login);

            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User with given login was not found");
        }
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
