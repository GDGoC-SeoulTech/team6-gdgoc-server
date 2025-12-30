package com.gdgoc.arcive.domain.session.exception;

import com.gdgoc.arcive.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SessionErrorCode implements BaseErrorCode {

    SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "SESSION-001", "존재하지 않는 세션입니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}