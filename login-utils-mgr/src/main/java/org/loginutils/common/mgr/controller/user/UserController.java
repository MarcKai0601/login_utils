package org.loginutils.common.mgr.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dal.model.UserDo;
import org.loginutils.common.dal.mappers.UserMapper;
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
    public String Login(@RequestBody UserDo user) {

        log.info(user.toString());
        if (user.getUsername() != null) {
            return  userMapper.findList(user.getUsername()).toString();
        }
        return "Fail";
    }

    @PostMapping("/add")
    public String add(@RequestBody UserDo user) {

        log.info(user.toString());
        if (user.getUsername() != null) {
            return  userMapper.findList(user.getUsername()).toString();
        }
        return "Fail";
    }

//    public static void main(String[] args) {
//        boolean a ="OK".equals(HttpStatus.OK.getReasonPhrase());
//        System.out.println(a);
//        System.out.println(HttpStatus.OK);
//        System.out.println(HttpStatus.OK.getReasonPhrase());
//        System.out.println(HttpStatus.OK.getClass());
//        System.out.println(HttpStatus.OK);
//    }
}
