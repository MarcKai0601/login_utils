package org.loginutils.mgr.utils;

import org.apache.commons.lang3.RandomStringUtils;

import static org.loginutils.dto.UserDto.DEFAULT_PASSWORD_LENGTH;

public final class UserUtil {

    private UserUtil() {
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_PASSWORD_LENGTH);
    }

    public static String makeFullPath(String parentPath, Long userId) {
        if (parentPath == null) {
            return User.PATH_SEPARATOR + userId + User.PATH_SEPARATOR;
        } else {
            return parentPath + userId + User.PATH_SEPARATOR;
        }
    }

    public static boolean isSuperAdmin(SessionDto sessionDto) {
        return sessionDto.getLevel() == User.LEVEL_SUPER_ADMIN && sessionDto.getRoleId() == Role.SUPER_ADMIN;
    }

    public static boolean isSuperAdminUser(SessionDto sessionDto) {
        return sessionDto.getLevel() == User.LEVEL_SUPER_ADMIN && sessionDto.getRoleId() != Role.SUPER_ADMIN;
    }
}
