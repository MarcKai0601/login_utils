package org.loginutils.common.mgr.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.loginutils.common.dto.RoleDto;
import org.loginutils.common.dto.SessionDto;
import org.loginutils.common.dto.UserDto;

import static org.loginutils.common.dto.UserDto.DEFAULT_PASSWORD_LENGTH;

public final class UserUtil {

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_PASSWORD_LENGTH);
    }

    public static String makeFullPath(String parentPath, Long userId) {
        if (parentPath == null) {
            return UserDto.PATH_SEPARATOR + userId + UserDto.PATH_SEPARATOR;
        } else {
            return parentPath + userId + UserDto.PATH_SEPARATOR;
        }
    }

    public static boolean isSuperAdmin(SessionDto sessionDto) {
        return sessionDto.getLevel() == UserDto.LEVEL_SUPER_ADMIN && sessionDto.getRoleId() == RoleDto.SUPER_ADMIN;
    }

    public static boolean isSuperAdminUser(SessionDto sessionDto) {
        return sessionDto.getLevel() == UserDto.LEVEL_SUPER_ADMIN && sessionDto.getRoleId() != RoleDto.SUPER_ADMIN;
    }
}
