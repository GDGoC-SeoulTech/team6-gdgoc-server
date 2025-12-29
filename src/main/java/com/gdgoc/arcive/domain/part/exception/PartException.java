package com.gdgoc.arcive.domain.part.exception;

import com.gdgoc.arcive.global.exception.BaseException;
import lombok.Getter;

@Getter
public class PartException extends BaseException {

    public PartException(PartErrorCode errorCode) {
        super(errorCode);
    }
}