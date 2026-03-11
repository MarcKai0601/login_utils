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
    private static final String USER_TOKEN_PREFIX = "user_token:";
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
            // Step 1 & 2: 查詢的反向索引並刪除舊 Token
            String userTokenKey = USER_TOKEN_PREFIX + userDto.getUserId();
            String oldToken = stringRedisTemplate.opsForValue().get(userTokenKey);
            if (oldToken != null && !oldToken.isEmpty()) {
                stringRedisTemplate.delete(TOKEN_PREFIX + oldToken);
                log.info("Kick out old connection: userId={}, oldToken={}", userDto.getUserId(), oldToken);
            }

            // Step 3: 更新新的 Session 資料
            String jsonValue = objectMapper.writeValueAsString(sessionData);
            stringRedisTemplate.opsForValue().set(key, jsonValue, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);

            // Step 4: 刷新反向索引並設定同樣的過期時間
            stringRedisTemplate.opsForValue().set(userTokenKey, token, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize session data for userId={}", userDto.getUserId(), e);
            // Fallback: 僅存 userId
            stringRedisTemplate.opsForValue().set(key, String.valueOf(userDto.getUserId()), TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
            stringRedisTemplate.opsForValue().set(USER_TOKEN_PREFIX + userDto.getUserId(), token, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
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
            try {
                // 嘗試解析 JSON取得 userId 以延伸 user_token 快取
                Map<String, Object> sessionData = objectMapper.readValue(jsonValue, Map.class);
                if (sessionData.containsKey("userId")) {
                    Object userIdVal = sessionData.get("userId");
                    stringRedisTemplate.expire(USER_TOKEN_PREFIX + userIdVal.toString(), TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
                }

                // 重置主要 Token 存活時間
                stringRedisTemplate.expire(key, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
                return sessionData;
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

