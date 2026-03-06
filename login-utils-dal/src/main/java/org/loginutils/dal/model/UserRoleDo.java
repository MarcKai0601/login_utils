package org.loginutils.dal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用戶角色視圖 DO — 用於映射 k_user_role JOIN k_role JOIN k_system 的查詢結果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDo {

    private Long id;

    private Long userId;

    private Long roleId;

    private String roleCode;

    private String roleName;

    private Long systemId;

    private String systemCode;

    private String systemName;
}
