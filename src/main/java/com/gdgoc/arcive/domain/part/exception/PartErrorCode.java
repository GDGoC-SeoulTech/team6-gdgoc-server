package com.gdgoc.arcive.domain.part.exception;

import com.gdgoc.arcive.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PartErrorCode implements BaseErrorCode {

    PART_NOT_FOUND(HttpStatus.NOT_FOUND, "PART-001", "존재하지 않는 파트입니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}