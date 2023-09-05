package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {
    
    // yml 파일에서 할 수 있는 라우팅 작업을 자바 코드로 하는 방법
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // 들어올 라우트 정보에 path 등록
                // 헤더 정보를 등록후 어디로 보낼지 uri에 작성
                .route(r -> r.path("/first-service/**")
                            .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                           .addResponseHeader("first-response", "first-response-header"))
                            .uri("http://localhost:8081") )
                .route(r -> r.path("/second-service/**")
                            .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                           .addResponseHeader("second-response", "second-response-header"))
                            .uri("http://localhost:8082") )
                .build();
    }
}
