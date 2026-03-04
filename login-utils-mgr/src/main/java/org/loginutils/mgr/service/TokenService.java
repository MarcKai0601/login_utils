package org.loginutils.mgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final String TOKEN_PREFIX = "token:";
    private static final long EXPIRATION_HOURS = 24;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String generateToken(Long userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        String key = TOKEN_PREFIX + token;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(userId), EXPIRATION_HOURS, TimeUnit.HOURS);
        return token;
    }
}
