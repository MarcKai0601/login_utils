package org.loginutils.mgr.config;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DynamicCorsConfigurationSource implements CorsConfigurationSource {

    private final RedisTemplate<String, String> redisTemplate;
    public static final String CORS_WHITELIST_KEY = "system:cors:whitelist";

    public DynamicCorsConfigurationSource(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        // 1. 取得前端發出請求的網域
        String origin = request.getHeader("Origin");

        if (origin == null) {
            return null; // 非跨域請求，不需要 CORS header
        }

        // 2. 去 Redis 檢查這個網域是否在白名單內 (Redis Set 的 SISMEMBER 指令)
        Boolean isAllowed = redisTemplate.opsForSet().isMember(CORS_WHITELIST_KEY, origin);

        if (Boolean.TRUE.equals(isAllowed)) {
            // 3. 如果在白名單內，動態生成並放行
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of(origin)); // 只允許這個合法的來源
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(List.of("*"));
            config.setExposedHeaders(List.of("Authorization"));
            config.setAllowCredentials(true);
            return config;
        }

        // 4. 不在白名單，拒絕跨域
        return null;
    }
}