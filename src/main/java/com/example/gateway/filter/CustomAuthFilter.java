package com.example.gateway.filter;

import com.example.gateway.exception.CustomException;
import com.example.gateway.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.example.gateway.exception.ErrorCode.AUTH_TOKEN_NOT_FOUND;
import static com.example.gateway.exception.ErrorCode.INVALID_AUTH_TOKEN;

@Component
@Log4j2
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    public CustomAuthFilter() {
        super(Config.class);
    }

    /**
     * Spring Cloud 는 webFlux 기반이므로 mvc에서 사용하던 에러핸들러를 사용할 수 없다.
     * webFlux 에서 제공하는 에러핸들러는 ErrorWebExceptionHandler, AbstractErrorWebExceptionHandler, DefaultErrorWebExceptionHandler 이다.
    **/
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey("token")){
               throw new CustomException(AUTH_TOKEN_NOT_FOUND);
            }

            List<String> token = request.getHeaders().get("token");
            String tokenString = Objects.requireNonNull(token).get(0);

            if(!tokenString.equals("hello token")) {
                throw new CustomException(INVALID_AUTH_TOKEN);
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
