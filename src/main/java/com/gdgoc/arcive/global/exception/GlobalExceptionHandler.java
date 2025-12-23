package com.gdgoc.arcive.global.exception;

import com.gdgoc.arcive.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(BaseException e) {
        log.warn("[!] Exception: {}", e);

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(ApiResponse.failure(e.getErrorCode()));
    }
}
