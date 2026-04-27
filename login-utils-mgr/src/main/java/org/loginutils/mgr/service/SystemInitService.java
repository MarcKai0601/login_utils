package org.loginutils.mgr.service;

import jakarta.annotation.PostConstruct;
import org.loginutils.dal.mappers.SystemMapper;
import org.loginutils.dal.model.SystemDo;
import org.loginutils.mgr.config.DynamicCorsConfigurationSource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemInitService {

    private final SystemMapper systemMapper;
    private final StringRedisTemplate redisTemplate;

    // 使用建構子注入
    public SystemInitService(SystemMapper systemMapper, StringRedisTemplate redisTemplate) {
        this.systemMapper = systemMapper;
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void initCorsWhitelistToRedis() {
        // 1. 清空舊的白名單 (避免有已經刪除的網域殘留)
        redisTemplate.delete(DynamicCorsConfigurationSource.CORS_WHITELIST_KEY);

        // 2. 從資料庫撈出所有系統設定
        List<SystemDo> systems = systemMapper.selectAll();

        // 3. 把資料庫的 domain 塞進 Redis 的 Set 裡面
        if (systems != null && !systems.isEmpty()) {
            for (SystemDo sys : systems) {
                // 防呆：確認有值且去除前後空白
                if (sys.getDomain() != null && !sys.getDomain().trim().isEmpty()) {
                    redisTemplate.opsForSet().add(
                            DynamicCorsConfigurationSource.CORS_WHITELIST_KEY,
                            sys.getDomain().trim()
                    );
                }
            }
            System.out.println("CORS 白名單已成功從 DB (k_system) 載入至 Redis！");
        } else {
            System.out.println("CORS 警告：DB (k_system) 中沒有找到任何有效的網域設定。");
        }
    }
}