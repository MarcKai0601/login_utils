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
public class SystemDo {
    private Long systemId;
    private String systemCode;
    private String systemName;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;
}
