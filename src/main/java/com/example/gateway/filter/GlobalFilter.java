package com.example.gateway.filter;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            if(config.usePreFilter) {
                log.info("Global filter request id : {}", request.getId());
                log.info("Global filter request method : {}", request.getMethod());
                log.info("Global filter request path : {}", request.getPath());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.usePostFilter) {
                    log.info("Global filter response code : {}", response.getStatusCode());
                    // 트래픽 정보 DB 저장 (전체/모듈별)
                }
            }));
        });
    }

    @Data
    public static class Config {
        private boolean usePreFilter;
        private boolean usePostFilter;
    }

}
