package org.loginutils.mgr.controller.login;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loginutils.common.dto.UserDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Max(UserDto.STATUS_ENABLED)
    @Min(UserDto.STATUS_DISABLED)
    private Integer status;

    private String memo;

//    @NotNull
//    private Long roleId;

    private String nickname;

    private String email;

    private String phone;

}
