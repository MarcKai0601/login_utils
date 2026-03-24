package org.loginutils.mgr.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.UserDto;
import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.loginutils.dal.model.UserDo;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.mgr.service.UserService;
import org.loginutils.mgr.service.TokenService; // 記得引入 TokenService
import org.loginutils.common.exception.MgrException; // 引入自訂例外
import org.loginutils.common.enums.MgrResponseCode; // 引入 Enum
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/mgr/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    // 💡 修正 1：將 TokenService 的注入移到 Class 層級
    @Autowired
    TokenService tokenService;

    @PostMapping("/add")
    @ResponseBody
    public MgrResponseDto addUser(@RequestBody AddUserRequest addUserRequest) throws MgrException {

        UserDto userDto = UserDto.builder()
                .username(addUserRequest.getUsername())
                .password(addUserRequest.getPassword())
                .status(addUserRequest.getStatus())
                .email(addUserRequest.getEmail())
                .build();

        log.info(addUserRequest.toString());

        userService.addUser(userDto);

        return MgrResponseDto.success("SUCCESS");

    }

    // 💡 修正 2：簡化參數列，並使用正常的 Import
    @PutMapping("/update-password")
    public MgrResponseDto updatePassword(
            @RequestBody @Valid UpdatePasswordRequest request,
            HttpServletRequest httpRequest) throws MgrException {

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new MgrException(MgrResponseCode.INVALID_TOKEN, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Map<String, Object> userData = tokenService.verifyAndExtendToken(token);

        if (userData == null || !userData.containsKey("userId")) {
            throw new MgrException(MgrResponseCode.INVALID_TOKEN, "Token is invalid or expired");
        }

        // 💡 修正 3：確保此處的轉型符合你 UserService 預期的 userId 型態 (Integer 或 Long)
        // 假設你的系統 userId 是 Integer，請改為 Integer.valueOf(...)
        Long userId = Long.valueOf(userData.get("userId").toString());

        userService.updatePassword(userId, request.getOldPassword(), request.getNewPassword());

        return MgrResponseDto.success("密碼修改成功");
    }
}