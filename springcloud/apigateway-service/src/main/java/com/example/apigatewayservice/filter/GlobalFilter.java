package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
// 커스텀 필터를 만든후 application 설정 파일안에 선언
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    // apply 함수를 오버라이드 해서 작업 하고 싶은 내용을 작성
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter base message : {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Global Filter start : request id -> {}", request.getId());
            }
            // custom Post Filter
            // Mono : webflux, spring 5에 추가됨, 비동기 서버 지원, 단위값 반환
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Global Filter End: response code -> {}", response.getStatusCode());
                }
            }));
        };
    }

    @Data
    public static class Config {
        // configuration 정보를 담을 수 있다
        private  String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
