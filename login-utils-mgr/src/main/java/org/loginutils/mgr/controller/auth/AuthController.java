package org.loginutils.mgr.controller.auth;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.loginutils.common.enums.MgrResponseCode;
import org.loginutils.common.exception.MgrException;
import org.loginutils.mgr.service.TokenService;
import org.loginutils.mgr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Resource
    private UserService userService;

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

    @PostMapping("/forgot-password")
    public MgrResponseDto forgotPassword(@RequestBody ForgotPasswordRequest request) throws MgrException {

        if (request.getEmail().isBlank())
            throw new MgrException(MgrResponseCode.EMAIL_REQUIRED, "請填寫信箱");

        try {
            // 直接使用注入好的 userService
            userService.resetPassword(request.getEmail());
            return MgrResponseDto.success("Password reset instructions have been sent to your email.");
        } catch (MgrException e) {
            return MgrResponseDto.error(MgrResponseCode.EMAIL_NOT_EXISTS);
        }
    }
}
