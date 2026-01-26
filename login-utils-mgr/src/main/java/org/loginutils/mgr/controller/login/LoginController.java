package org.loginutils.mgr.controller.login;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.UserDto;
import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.loginutils.common.enums.MgrResponseCode;
import org.loginutils.common.exception.MgrException;
import org.loginutils.mgr.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public MgrResponseDto Login(@RequestBody @Valid LoginRequest loginRequest) throws MgrException {

        log.info(loginRequest.toString());

        UserDto loginDto = UserDto.builder()
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .build();

        loginDto = loginService.login(loginDto);
//        if (loginDto.getUsername() != null && loginDto.getPassword() != null) {
//            return loginService.login(loginDto);
//        }
//        return MgrResponseDto.error(MgrResponseCode.USER_PASSWORD_INVALID);
        return MgrResponseDto.success(loginDto);

    }
}
