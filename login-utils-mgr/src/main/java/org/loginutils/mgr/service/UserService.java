package org.loginutils.mgr.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.loginutils.common.dto.UserDto;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.dal.mappers.UserRoleMapper;
import org.loginutils.dal.model.UserDo;
import org.loginutils.dal.model.UserRoleDo;
import org.loginutils.common.exception.MgrException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class UserService {

    /**
     * 預設角色 ID — 對應 k_role 表中「一般使用者」角色
     * 請確保 k_role 表中存在此記錄（例如 RoleId=2, RoleCode='USER'）
     */
    private static final Long DEFAULT_ROLE_ID = 2L;

    @Resource
    UserMapper userMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    EmailService emailService;

    private static final Long DEFAULT_GLOBAL_ROLE_ID = 3L;

    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserDto userDto) throws MgrException {

        // 檢查 Email 是否已存在
        if (userDto.getEmail() != null && userMapper.selectByUsernameOrEmail(userDto.getEmail()) != null) {
            log.warn("Registration blocked: Email {} already exists", userDto.getEmail());
            throw new MgrException(org.loginutils.common.enums.MgrResponseCode.EMAIL_ALREADY_EXISTS);
        }

        String lang = (userDto.getLanguage() != null && !userDto.getLanguage().trim().isEmpty())
                ? userDto.getLanguage()
                : "zh-TW";

        UserDo user = UserDo.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .language(lang)
                .pwdResetCount(0)
                .build();

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        // insert 後 MyBatis 會自動回填 userId（useGeneratedKeys）
        userMapper.insert(user);

        log.info("用戶註冊成功 username={}, userId={}", user.getUsername(), user.getUserId());

        // 修改點 2：自動綁定預設全域角色，並且必須加上 SystemId
        // 這裡的 userDto.getSystemId() 需要你從前端 Request 一路傳進來
        if (userDto.getSystemId() == null) {
            throw new MgrException(org.loginutils.common.enums.MgrResponseCode.PARAM_INVALID, "SystemId is required for assigning roles");
        }

        UserRoleDo userRole = UserRoleDo.builder()
                .userId(user.getUserId())
                .systemId(userDto.getSystemId()) // <--- 關鍵！綁定所屬系統
                .roleId(DEFAULT_GLOBAL_ROLE_ID)  // <--- 使用全域 USER 角色
                .build();
        userRoleMapper.insertUserRole(userRole);

        log.info("已為 userId={} 在系統 systemId={} 綁定預設角色 roleId={}",
                user.getUserId(), userDto.getSystemId(), DEFAULT_GLOBAL_ROLE_ID);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, String oldPassword, String newPassword) throws MgrException {
        UserDo user = userMapper.findById(userId);
        if (user == null) {
            log.warn("updatePassword failed: userId={} not found", userId);
            throw new MgrException(org.loginutils.common.enums.MgrResponseCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            log.warn("updatePassword failed: incorrect old password for userId={}", userId);
            throw new MgrException(org.loginutils.common.enums.MgrResponseCode.USER_PASSWORD_INVALID, "舊密碼錯誤");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        UserDo updateRecord = UserDo.builder()
                .userId(userId)
                .password(encodedNewPassword)
                .isTempPassword(0)
                .build();

        userMapper.update(updateRecord);
        log.info("Successfully updated password for userId={}", userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String email) throws MgrException {
        UserDo user = userMapper.selectByUsernameOrEmail(email);
        if (user == null) {
            log.warn("Forgot Password failed: user mapped to email {} not found", email);
            throw new MgrException(org.loginutils.common.enums.MgrResponseCode.USER_NOT_FOUND);
        }

        Date now = new Date();
        Integer currentCount = user.getPwdResetCount() != null ? user.getPwdResetCount() : 0;
        Date windowStart = user.getPwdResetWindowStart();

        long THIRTY_DAYS_MS = 30L * 24 * 60 * 60 * 1000;

        if (windowStart == null || (now.getTime() - windowStart.getTime() > THIRTY_DAYS_MS)) {
            // New Window
            windowStart = now;
            currentCount = 1;
        } else {
            // Extant Window
            if (currentCount >= 3) {
                log.warn("Forgot Password limit exceeded for userId={}", user.getUserId());
                throw new MgrException(org.loginutils.common.enums.MgrResponseCode.PWD_RESET_LIMIT_EXCEEDED);
            }
            currentCount++;
        }

        // 使用密碼學安全的亂數產生器
        String newRawPassword = RandomStringUtils.secure().nextAlphanumeric(8);
        String encodedPassword = passwordEncoder.encode(newRawPassword);

        UserDo updateRecord = UserDo.builder()
                .userId(user.getUserId())
                .password(encodedPassword)
                .pwdResetCount(currentCount)
                .pwdResetWindowStart(windowStart)
                .isTempPassword(1)
                .build();

        userMapper.update(updateRecord);

        // Send out notification via real SMTP
        emailService.sendPasswordResetEmail(email, newRawPassword);
    }
}
