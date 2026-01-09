package org.loginutils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    public static final int STATUS_DISABLED = 0;
    public static final int STATUS_ENABLED = 1;

    // 預設 roleId
    public static final long SUPER_ADMIN = 0;
    public static final long AGENT_ADMIN = 1;
    public static final long MERCHANT_ADMIN = 3;

}
