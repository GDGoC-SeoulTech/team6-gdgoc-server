package com.gdgoc.arcive.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getStatus();
    String getValue();
    String getMessage();
}