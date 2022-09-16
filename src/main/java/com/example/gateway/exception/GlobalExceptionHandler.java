package com.example.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Order(-1)
@Component
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        String errorJson = getErrorJson(exchange, ex);

        byte[] bytes = errorJson.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }


    private String getErrorJson(ServerWebExchange exchange, Throwable ex) {

        Map<String, String> errorMap = new HashMap<>();
        ServerHttpRequest request = exchange.getRequest();

        if (ex.getClass() == NullPointerException.class) {
            errorMap.put("errorCode", "123");
            errorMap.put("errorMsg", "Null 입니다");
        } else if (ex.getClass() == CustomException.class) {
            CustomException customException = (CustomException) ex;
            errorMap.put("errorCode", customException.getErrorCode().getHttpStatus().toString());
            errorMap.put("errorMsg", customException.getErrorCode().getMessage());
        } else {
            errorMap.put("errorCode", "9999");
            errorMap.put("errorMsg", "알 수 없는 오류");
        }

        errorMap.put("path", request.getPath().toString());

        ObjectMapper mapper = new ObjectMapper();
        String errorJson = "";

        try {
            errorJson = mapper.writeValueAsString(errorMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return errorJson;
    }
}
