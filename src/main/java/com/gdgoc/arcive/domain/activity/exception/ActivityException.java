package com.gdgoc.arcive.domain.activity.exception;

import com.gdgoc.arcive.global.exception.BaseException;
import lombok.Getter;

@Getter
public class ActivityException extends BaseException {

    public ActivityException(ActivityErrorCode errorCode) {
        super(errorCode);
    }
}