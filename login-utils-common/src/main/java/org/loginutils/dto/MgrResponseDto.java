package org.loginutils.dto;

import org.loginutils.enums.MgrResponseCode;

public class MgrResponseDto<T> {
    public static <T> MgrResponseDto<T> success(T data) {
        MgrResponseDto<T> dto = new MgrResponseDto<>();
        dto.setCode(MgrResponseCode.SUCCESS.getCode());
        dto.setData(data);
        return dto;
    }

    public static <T> MgrResponseDto<T> error(MgrResponseCode code) {
        MgrResponseDto<T> dto = new MgrResponseDto<>();
        dto.setCode(code.getCode());
        dto.setMessage(code.getMessage());
        return dto;
    }

    public static MgrResponseDto<Void> success() {
        return success(null);
    }

    private String code;

    private String message;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(MgrResponseCode code) {
        this.code = code.getCode();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
