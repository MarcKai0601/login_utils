package org.loginutils.dal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDo {
    private Long roleId;
    private String roleCode;
    private String roleName;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;
}
