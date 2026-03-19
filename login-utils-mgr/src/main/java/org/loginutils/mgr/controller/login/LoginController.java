package org.loginutils.mgr.controller.login;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.RoleDto;
import org.loginutils.common.dto.SessionDto;
import org.loginutils.common.dto.UserDto;
import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.loginutils.common.enums.MgrResponseCode;
import org.loginutils.common.exception.MgrException;
import org.loginutils.mgr.service.LoginService;
import org.loginutils.mgr.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/login")
    public MgrResponseDto Login(@RequestBody @Valid LoginRequest loginRequest,
                                HttpServletRequest httpServletRequest,
                                HttpServletResponse servletResponse) throws MgrException {

        log.info(loginRequest.toString());

//        TODO: 抓取用戶的IP 但是並不完善 因為有可能在不同的Header 有IP
//        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        String ip = org.loginutils.mgr.utils.IpUtility.getRequestIp(httpServletRequest);
        UserDto loginDto = UserDto.builder()
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .loginIp(ip)
                .build();

        loginDto = loginService.login(loginDto);

        String timezone = TimeZone.getDefault().getID();

        SessionDto sessionDto = SessionDto.builder()
                .userId(loginDto.getUserId())
                .username(loginDto.getUsername())
                .status(loginDto.getStatus())
                .language(loginDto.getLanguage())
                .ip(ip)
                .timezone(timezone)
                .build();

        String token = tokenService.generateToken(loginDto);

        servletResponse.setHeader("Authorization", "Bearer " + token);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .userId(loginDto.getUserId())
                .roles(loginDto.getRoles())
                .build();

        log.info("準備回傳給前端的 Roles: {}", loginDto.getRoles());
        return MgrResponseDto.success(loginResponse);

    }
}
