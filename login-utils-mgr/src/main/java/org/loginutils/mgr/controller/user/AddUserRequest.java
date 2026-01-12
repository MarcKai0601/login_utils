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
class AddUserRequest {
    @NotBlank
    private String username;

    @Size(min = UserDto.DEFAULT_PASSWORD_LENGTH)
    private String password;

    @Max(UserDto.STATUS_ENABLED)
    @Min(UserDto.STATUS_DISABLED)
    private Integer status;

    private String memo;

    @NotNull
    private Long roleId;

    private String nickname;

    private String email;

    private String phone;
}