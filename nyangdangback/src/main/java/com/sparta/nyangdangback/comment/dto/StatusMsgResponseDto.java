package com.sparta.nyangdangback.comment.dto;

import lombok.Builder;

public class StatusMsgResponseDto {
    private String msg; // 상태메시지
    private Integer msgCode; // 상태 코드

    @Builder
    public StatusMsgResponseDto(String msg, Integer msgCode) {
        this.msg = msg;
        this.msgCode = msgCode;
    }
}
