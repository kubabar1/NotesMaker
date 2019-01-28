package com.od.notesmaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private static final String LOGIN_ATTEMPT_KEY = "NOTES_MAKER:LOGIN_ATTEMPT";

    private static final int MAX_ATTEMPT = 10;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void loginSucceeded(String login) {
        String hash = LOGIN_ATTEMPT_KEY + ":" + login;
        Optional.ofNullable(redisTemplate.opsForHash().get(hash, "attempts")).ifPresent(x -> redisTemplate.delete(hash));
    }

    @Override
    public void loginFailed(String login) {
        String hash = LOGIN_ATTEMPT_KEY + ":" + login;
        int attempts = (int) Optional.ofNullable(redisTemplate.opsForHash().get(hash, "attempts")).orElse(0);
        attempts++;
        redisTemplate.opsForHash().put(hash, "attempts", attempts);
        redisTemplate.expire(hash, 24, TimeUnit.HOURS);
    }

    @Override
    public boolean isBlocked(String login) {
        String hash = LOGIN_ATTEMPT_KEY + ":" + login;
        return (Integer) Optional.ofNullable(redisTemplate.opsForHash().get(hash, "attempts")).orElse(0) >= MAX_ATTEMPT;
    }


}
