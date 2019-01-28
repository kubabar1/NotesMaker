package com.od.notesmaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class LastLoginServiceImpl implements LastLoginService {

    private static final String LOGIN_ATTEMPT_KEY = "NOTES_MAKER:LAST_LOGINS";

    private static final int MAX_LAST_LOGINS_HISTORY_SIZE = 10;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void addLastLogin(String ip, String login) {
        String hash = LOGIN_ATTEMPT_KEY + ":" + login;

        LinkedList<String> lastLogins = (LinkedList<String>) Optional.ofNullable(redisTemplate.opsForHash()
                .get(hash, "last_logins")).orElse(new LinkedList<>());

        System.out.println("Adding new IP address: " + ip);

        if (lastLogins.size() < MAX_LAST_LOGINS_HISTORY_SIZE) {
            lastLogins.push(ip);
            redisTemplate.opsForHash().put(hash, "last_logins", lastLogins);
        } else {
            lastLogins.removeLast();
            lastLogins.push(ip);
            redisTemplate.opsForHash().put(hash, "last_logins", lastLogins);
        }

    }

    @Override
    public List<String> getAllLastLogins(String login) {
        String hash = LOGIN_ATTEMPT_KEY + ":" + login;
        return (LinkedList<String>) Optional.ofNullable(redisTemplate.opsForHash()
                .get(hash, "last_logins")).orElse(new LinkedList<>());
    }
}
