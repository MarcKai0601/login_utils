package org.loginutils.dal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDo {

    private Long userId;// 用戶ID(唯一)

    private String email;

    private String username;// 用戶帳號

    private String password;// 登錄密碼

    private Integer status;// 啟用狀態

    private Date createTime;// 創建時間

    private Date updateTime;// 修改時間

    private String loginIp;// 最後登錄IP

    private Date loginTime;// 最後登錄時間

    private Long failedLoginCount; // 連續登入失敗次數

    private String memo; // 備註

}
