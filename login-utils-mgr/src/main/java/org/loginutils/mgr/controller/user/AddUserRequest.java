package org.loginutils.mgr.controller.user;

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
public class AddUserRequest {

    //    TODO: 需要加上Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "使用者名稱不得為空白")
    private String username;

    @NotBlank
    @Size(min = UserDto.DEFAULT_PASSWORD_LENGTH)
    private String password;

    @Max(UserDto.STATUS_ENABLED)
    @Min(UserDto.STATUS_DISABLED)
    private Integer status;

    private String memo;

    @NotNull
    private Long roleId;

    private String nickname;

    private String phone;
    
    private String language;

    @NotNull(message = "系統ID不能為空")
    private Long systemId;
}