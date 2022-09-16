package com.example.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    AUTH_TOKEN_NOT_FOUND(UNAUTHORIZED, "토큰을 입력하지 않았습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
