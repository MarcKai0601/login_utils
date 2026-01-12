package org.loginutils.common.dto;

public class UserDto {
    public static final String PATH_SEPARATOR = "/";

    public static final long ADMIN_USER_ID = 1L;

    public static final int STATUS_DISABLED = 0;
    public static final int STATUS_ENABLED = 1;

    public static final int LEVEL_MERCHANT = -2; // 商戶管理員
    public static final int LEVEL_SUPER_ADMIN = -1; // Super Admin
    public static final int LEVEL_GENERAL_AGENT = 0; // 代理管理員

    public static final int DEFAULT_PASSWORD_LENGTH = 8;

}
