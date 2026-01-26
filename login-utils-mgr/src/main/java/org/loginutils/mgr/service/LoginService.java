package org.loginutils.mgr.service;

import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.UserDto;
import org.loginutils.common.enums.MgrResponseCode;
import org.loginutils.common.exception.MgrException;
import org.loginutils.common.utils.hash.MD5Utils;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.dal.model.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

//    public UserLogin login(String username, String password, String totpCode, String ip, String merchant) throws XxPayMgrException {
//        log.info("username : "+username);
//        UserLogin user = userMapper.findLogin(username, merchant);
//
//        if (user == null) {
//            log.info("username={} does not exist", username);
//            throw new XxPayMgrException(MgrResponseCode.USER_NOT_FOUND, username);
//        }
//
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
//        if (user.getStatus() == 0) {
//            log.info("username={} login status is prohibited", username);
//            throw new XxPayMgrException(MgrResponseCode.USER_DISABLED, username);
//        }
//
//        if (!user.getPassword().equals(MD5Utils.getMD5String(password))) {
//            log.info("(username={}, password={}) password is wrong", username, password);
//            userMapper.increaseFailedLogin(user.getUserId());
//            if (user.getFailedLoginCount() >= FAILED_LOGIN_MAX_COUNT - 1) {
//                User record = User.builder()
//                        .userId(user.getUserId())
//                        .status(User.STATUS_DISABLED)
//                        .build();
//                userMapper.update(record);
//            }
//            throw new XxPayMgrException(MgrResponseCode.USER_PASSWORD_INVALID);
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
//        log.info("登录成功 username={}, IP={}", username, ip);
//
//        return user;
//    }

    public UserDto login(UserDto userDto) throws MgrException {

        String username = userDto.getUsername();

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

        if (!user.getPassword().equals(MD5Utils.getMD5String(userDto.getPassword()))) {
            log.info("(username={}, password={}) password is wrong", username, userDto.getPassword());
//            userMapper.increaseFailedLogin(user.getUserId()); TODO:紀錄登錄失敗次數
//            if (user.getFailedLoginCount() >= FAILED_LOGIN_MAX_COUNT - 1) {
//                User record = User.builder()
//                        .userId(user.getUserId())
//                        .status(User.STATUS_DISABLED)
//                        .build();
//                userMapper.update(record);
//            }
            throw new MgrException(MgrResponseCode.USER_PASSWORD_INVALID);
        }

        log.info("登录成功 username={}", username);

        UserDto userDtoRepson = UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .status(user.getStatus())
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
