package com.gdgoc.arcive.domain.member.exception;

import com.gdgoc.arcive.global.exception.BaseException;

public class MemberException extends BaseException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}