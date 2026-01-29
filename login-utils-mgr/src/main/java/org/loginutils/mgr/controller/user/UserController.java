package org.loginutils.mgr.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.UserDto;
import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.loginutils.dal.model.UserDo;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.mgr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/mgr/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public MgrResponseDto addUser(@RequestBody AddUserRequest addUserRequest) {

        UserDto userDto = UserDto.builder()
                .username(addUserRequest.getUsername())
                .password(addUserRequest.getPassword())
                .status(addUserRequest.getStatus())
                .build();

        log.info(addUserRequest.toString());

        userService.addUser(userDto);

        return MgrResponseDto.success("SUCCESS");

    }
}
