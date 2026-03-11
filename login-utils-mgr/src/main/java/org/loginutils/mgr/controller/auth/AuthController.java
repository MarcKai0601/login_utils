package org.loginutils.mgr.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.loginutils.common.enums.MgrResponseCode;
import org.loginutils.common.exception.MgrException;
import org.loginutils.mgr.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/me")
    public MgrResponseDto<Map<String, Object>> getCurrentUser(HttpServletRequest request) throws MgrException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new MgrException(MgrResponseCode.INVALID_TOKEN, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Map<String, Object> userData = tokenService.verifyAndExtendToken(token);

        if (userData == null) {
            throw new MgrException(MgrResponseCode.INVALID_TOKEN, "Token is invalid or expired");
        }

        return MgrResponseDto.success(userData);
    }
}
