package org.loginutils.mgr.controller.login;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.dal.model.UserDo;
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
    public String Login(@RequestBody UserDo user) {

        log.info(user.toString());
        if (user.getUsername() != null) {
            return  loginService.login(user);
        }
        return "Fail";
    }
}
