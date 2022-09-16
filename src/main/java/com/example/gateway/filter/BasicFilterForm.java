package com.example.gateway.filter;


import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BasicFilterForm extends AbstractGatewayFilterFactory<BasicFilterForm.Config> {

    public BasicFilterForm() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();    // 요청 데이터
            ServerHttpResponse response = exchange.getResponse(); // 응답 데이터

            //-------------- pre filter 작업 공간 ------------


            return chain.filter(exchange); // 해당 서비스로 이동
            /*
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                // ----------- post filter 작업 공간 -------------

            }));
             */
        });
    }


    // 해당 필터에 글로벌 파라미터 값이필요할 때 사용. (properties 에서 등록)
    @Data
    public static class Config {
    }
}
