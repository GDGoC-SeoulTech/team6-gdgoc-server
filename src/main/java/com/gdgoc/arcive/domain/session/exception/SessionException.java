package com.gdgoc.arcive.domain.session.exception;

import com.gdgoc.arcive.global.exception.BaseException;
import lombok.Getter;

@Getter
public class SessionException extends BaseException {

    public SessionException(SessionErrorCode errorCode) {
        super(errorCode);
    }
}