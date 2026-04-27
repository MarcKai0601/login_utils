package org.loginutils.mgr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // 1. 啟用 CORS 並套用下方的設定
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//        return http.build();
//    }
//
//    // 2. 建立 CORS 設定 Bean
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        // 設定允許跨域的前端來源 (開發階段可先設為 "*"，如果前端有帶 Cookie 需明確寫出如 "http://localhost:3000")
//        configuration.setAllowedOriginPatterns(List.of("*"));
////        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
//
//        // 允許的 HTTP 方法
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//
//        // 允許的 Header
//        configuration.setAllowedHeaders(List.of("*"));
//
//        // 允許前端取得的 Response Header (重要：你的 LoginController 會把 Token 放在 Authorization，前端要能讀取必須加這行)
//        configuration.setExposedHeaders(List.of("Authorization"));
//
//        // 若前端請求有設定 credentials: 'include' (帶 Cookie)，請將這行設為 true，且上面的 Origin 不能為 "*"
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        // 套用到所有 API 路徑
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


// 注入動態 CORS 檢查器
private final DynamicCorsConfigurationSource dynamicCorsSource;

    public SecurityConfig(DynamicCorsConfigurationSource dynamicCorsSource) {
        this.dynamicCorsSource = dynamicCorsSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 讓 Spring Security 使用你的動態 Redis 白名單機制
                .cors(cors -> cors.configurationSource(dynamicCorsSource))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
