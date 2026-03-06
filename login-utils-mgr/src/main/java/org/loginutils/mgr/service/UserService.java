package org.loginutils.mgr.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.UserDto;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.dal.mappers.UserRoleMapper;
import org.loginutils.dal.model.UserDo;
import org.loginutils.dal.model.UserRoleDo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserDto userDto) {

        UserDo user = UserDo.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .build();

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        // insert 後 MyBatis 會自動回填 userId（useGeneratedKeys）
        userMapper.insert(user);

        log.info("用戶註冊成功 username={}, userId={}", user.getUsername(), user.getUserId());

        // 自動綁定預設角色
        UserRoleDo userRole = UserRoleDo.builder()
                .userId(user.getUserId())
                .roleId(DEFAULT_ROLE_ID)
                .build();
        userRoleMapper.insertUserRole(userRole);

        log.info("已為 userId={} 綁定預設角色 roleId={}", user.getUserId(), DEFAULT_ROLE_ID);
    }
}
