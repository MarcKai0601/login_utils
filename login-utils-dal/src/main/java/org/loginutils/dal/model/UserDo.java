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

    private Integer userId;

    private String username;

    private String password;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
