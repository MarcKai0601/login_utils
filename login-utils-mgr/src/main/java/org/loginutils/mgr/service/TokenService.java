package org.loginutils.mgr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenService {

    private static final String TOKEN_PREFIX = "token:";
    private static final long EXPIRATION_HOURS = 24;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 生成 Token 並將用戶資訊（含多系統角色）存入 Redis
     */
    public String generateToken(UserDto userDto) {
        String token = UUID.randomUUID().toString().replace("-", "");
        String key = TOKEN_PREFIX + token;

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("userId", userDto.getUserId());
        sessionData.put("username", userDto.getUsername());
        sessionData.put("roles", userDto.getRoles());

        try {
            String jsonValue = objectMapper.writeValueAsString(sessionData);
            stringRedisTemplate.opsForValue().set(key, jsonValue, EXPIRATION_HOURS, TimeUnit.HOURS);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize session data for userId={}", userDto.getUserId(), e);
            // Fallback: 僅存 userId
            stringRedisTemplate.opsForValue().set(key, String.valueOf(userDto.getUserId()), EXPIRATION_HOURS, TimeUnit.HOURS);
        }

        return token;
    }
}

