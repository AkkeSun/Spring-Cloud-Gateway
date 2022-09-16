package com.example.gateway;

import com.example.gateway.filter.CustomAuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    /*
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder, CustomAuthFilter customAuthFilter) {
        return routeLocatorBuilder
                .routes()
                .route(r -> r
                        .path("/register/**")
                        .filters(f -> f
                                .addRequestHeader("first-request", "first-service-requestHeader")
                                .addResponseHeader("first-response", "first-service-responseHeader"))
                        .uri("http://localhost:8081"))

                .route(r -> r
                        .path("/getData/**")
                        .filters(f -> f
                                .addRequestHeader("second-request", "second-service-requestHeader")
                                .addResponseHeader("second-response", "second-service-responseHeader")
                                .filter(customAuthFilter.apply(new CustomAuthFilter.Config())))
                        .uri("http://localhost:8082"))
                .build();
    }
     */

}
