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
public class PermissionDo {
    private Long permissionId;
    private Long systemId;
    private Long parentId;
    private String permissionCode;
    private String permissionName;
    private Integer type;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;
}
