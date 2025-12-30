package com.gdgoc.arcive.domain.activity.exception;

import com.gdgoc.arcive.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Activity 관련 에러 코드
 * 코드 형식: ACTIVITY-001, ACTIVITY-002, ...
 */
@Getter
@AllArgsConstructor
public enum ActivityErrorCode implements BaseErrorCode {

    ACTIVITY_NOT_FOUND(HttpStatus.NOT_FOUND, "ACTIVITY-001","존재하지 않는 활동입니다."),
    ;

    private final HttpStatus status;
    private final String value;
    private final String message;
}