package org.loginutils.mgr.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.MgrResponseDto;
import org.loginutils.dal.model.UserDo;
import org.loginutils.dal.mappers.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mgr/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/add")
    public MgrResponseDto addUser(@RequestBody AddUserRequest addUserRequest) {

        UserDo user = UserDo.builder()
                .username(addUserRequest.getUsername())
                .password(addUserRequest.getPassword())
                .status(addUserRequest.getStatus())
                .build();

        log.info(addUserRequest.toString());

        userMapper.insert(user);
        return MgrResponseDto.success("");


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
