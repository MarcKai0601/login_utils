package org.loginutils.mgr.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.dal.model.User;
import org.loginutils.dal.mappers.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mgr")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/login")
    public String Login(@RequestBody User user) {

        log.info(user.toString());
        if (user.getUsername() != null) {
            return  userMapper.findList(user.getUsername()).toString();
        }
        return "Fail";
    }
}
