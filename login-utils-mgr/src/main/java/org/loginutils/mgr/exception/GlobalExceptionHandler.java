package org.loginutils.mgr.exception;

import lombok.extern.slf4j.Slf4j;
import org.loginutils.common.dto.repsonse.MgrResponseDto;
import org.loginutils.common.enums.MgrResponseCode;
import org.loginutils.common.exception.MgrException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MgrException.class)
    public MgrResponseDto<String> handleMgrException(MgrException e) {
        log.error("MgrException: [{}] {}", e.getCode(), e.getMessage());
        return MgrResponseDto.error(e.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MgrResponseDto<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("MethodArgumentNotValidException: {}", errorMessage);
        
        MgrResponseDto<String> response = MgrResponseDto.error(MgrResponseCode.PARAM_INVALID);
        response.setMessage(MgrResponseCode.PARAM_INVALID.getMessage() + ": " + errorMessage);
        return response;
    }

    @ExceptionHandler(Exception.class)
    public MgrResponseDto<String> handleException(Exception e) {
        log.error("Unhandled Exception: ", e);
        return MgrResponseDto.error(MgrResponseCode.UNKNOWN_ERROR);
    }
}
