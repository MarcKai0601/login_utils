package org.loginutils.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用戶角色詳情 DTO — 用於映射 k_user_role JOIN k_role JOIN k_system 的查詢結果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDetailDto {
    private Long userId;
    private Long roleId;
    private String roleCode;
    private String roleName;
    private Long systemId;
    private String systemCode;
    private String systemName;
}
