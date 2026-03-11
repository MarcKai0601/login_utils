package org.loginutils.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    protected String token;
//    protected List<AclDto> aclList;

    protected String username;

    protected Long userId;
    protected Long parentId; // 父代理ID
    protected String fullPath; // 代理階層路徑，含自己，並且斜線結尾

    protected Long roleId;
    protected String roleName;

    protected Integer status; // 状态

    protected Integer level; // 用戶層級 -1管理者, -2商戶

    protected String merchantId; // 對應的商戶ID
    protected String merchantName; // 對應的商戶成稱
    protected String merchantParentId; // 對應商戶的父代理ID
    protected String merchantFullPath; // 對應商戶的代理階層路徑，含自己，並且斜線結尾

    protected String nickname; // 用户名
    protected String ip; // ip

    protected String timezone;
    protected String language;
}
