package org.loginutils.mgr.service;

import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.RoleDto;
import org.loginutils.common.dto.UserDto;
import org.loginutils.common.dto.UserRoleDetailDto;
import org.loginutils.common.enums.MgrResponseCode;
import org.loginutils.common.exception.MgrException;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.dal.mappers.UserRoleMapper;
import org.loginutils.dal.model.UserDo;
import org.loginutils.dal.model.UserRoleDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoginService {

    private static final int FAILED_LOGIN_MAX_COUNT = 3;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDto login(UserDto userDto) throws MgrException {

        String username = userDto.getUsername();
        String ip = userDto.getLoginIp();

        log.info("username : " + username);
//        UserLogin user = userMapper.findLogin(username, merchant);

//      TODO:目前並沒有做權限（Role）相關的功能
        UserDo user = userMapper.findLogin(username);

        if (user == null) {
            log.info("username={} does not exist", username);
            throw new MgrException(MgrResponseCode.USER_NOT_FOUND, username);
        }

        if (user.getStatus() == 0) {
            log.info("username={} login status is prohibited", username);
            throw new MgrException(MgrResponseCode.USER_DISABLED, username);
        }

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            log.info("(username={}, password={}) password is wrong", username, userDto.getPassword());
            userMapper.increaseFailedLogin(user.getUserId());//  TODO:紀錄登錄失敗次數
            if (user.getFailedLoginCount() >= FAILED_LOGIN_MAX_COUNT - 1) {
                UserDo record = UserDo.builder()
                        .userId(user.getUserId())
                        .status(UserDto.STATUS_DISABLED)
                        .build();
                userMapper.update(record);
            }
            throw new MgrException(MgrResponseCode.USER_PASSWORD_INVALID);
        }

        userMapper.updateLogin(UserDo.builder()
                .userId(user.getUserId())
                .loginIp(ip)
                .build());

        log.info("登录成功 username={}, IP={}", username, ip);

        // 查詢該用戶在所有系統中的角色
        List<UserRoleDetailDto> userRoleDetails = userRoleMapper.findRolesByUserId(user.getUserId());
        List<RoleDto> roles = userRoleDetails.stream().map(ur -> {
            RoleDto role = new RoleDto();
            role.setRoleId(ur.getRoleId());
            role.setSystemId(ur.getSystemId());
            role.setRoleCode(ur.getRoleCode());
            role.setRoleName(ur.getRoleName());
            role.setSystemCode(ur.getSystemCode());
            role.setSystemName(ur.getSystemName());
            return role;
        }).collect(Collectors.toList());

        UserDto userDtoRepson = UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .status(user.getStatus())
                .roles(roles)
                .build();



//        if(user.getIsBackendLogin() == 0){
//            log.info("username={} merchant is disabled", username);
//            throw new XxPayMgrException(MgrResponseCode.MERCHANT_IS_DISABLED, username);
//        }
//
//        log.info("getUsername : "+user.getUsername());
//        if (user.getIsVerifySecretKey() == 1){
//            if(StringUtil.isEmpty(totpCode)){
//                throw new XxPayMgrException(MgrResponseCode.USER_TOTP_IS_REQUIRED, username);
//            }
//            boolean isSuccess = OtpUtil.verifyTotpCode(user.getSecretKey(), totpCode);
//            if (!isSuccess){
//                throw new XxPayMgrException(MgrResponseCode.USER_VERIFY_OTP_FAILURE, username);
//            }
//        }
//
//        if (user.getMerchantId() == null && user.getLevel() != User.LEVEL_SUPER_ADMIN) {
//            UserDetail parent = userMapper.findDetailById(user.getParentId());
//            user.setMerchantId(parent.getMerchantId());
//            user.setMerchantName(parent.getMerchantName());
//            user.setMerchantParentId(parent.getMerchantParentId());
//            user.setMerchantFullPath(parent.getMerchantFullPath());
//            user.setMerchantFullPath(parent.getMerchantFullPath());
//        }
//        log.info("user : "+user);
//        userMapper.updateLogin(User.builder().userId(user.getUserId()).loginIp(ip).build());
//

        return userDtoRepson;

    }
}
