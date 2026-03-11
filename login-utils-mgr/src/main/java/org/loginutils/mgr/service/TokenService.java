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
    private static final long TOKEN_EXPIRE_MINUTES = 30;

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
            stringRedisTemplate.opsForValue().set(key, jsonValue, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize session data for userId={}", userDto.getUserId(), e);
            // Fallback: 僅存 userId
            stringRedisTemplate.opsForValue().set(key, String.valueOf(userDto.getUserId()), TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
        }

        return token;
    }

    /**
     * 驗證 Token 是否存在且有效，若有效則使用滑動視窗展延其過期時間
     */
    public Map<String, Object> verifyAndExtendToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }
        String key = TOKEN_PREFIX + token;
        String jsonValue = stringRedisTemplate.opsForValue().get(key);
        
        if (jsonValue != null) {
            // 重置 Token 存活時間
            stringRedisTemplate.expire(key, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
            
            try {
                // 嘗試解析 JSON (包含 userId, roles 等)
                return objectMapper.readValue(jsonValue, Map.class);
            } catch (JsonProcessingException e) {
                log.error("Failed to deserialize session data from token={}", token, e);
                // 若解析失敗，但仍為有效的純文字 userId
                Map<String, Object> fallback = new HashMap<>();
                fallback.put("userId", jsonValue);
                return fallback;
            }
        }
        return null; // Token 不存在或已過期
    }
}

