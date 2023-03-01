package com.sparta.nyangdangback.comment.exception;

import com.sparta.nyangdangback.comment.entity.enumSet.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCodeEnum errorCode;
}
