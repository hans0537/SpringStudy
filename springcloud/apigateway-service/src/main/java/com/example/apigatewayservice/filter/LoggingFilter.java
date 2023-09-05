package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    // apply 함수를 오버라이드 해서 작업 하고 싶은 내용을 작성
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Global Filter base message : {}", config.getBaseMessage());
//
//            if (config.isPreLogger()) {
//                log.info("Global Filter start : request id -> {}", request.getId());
//            }
//            // custom Post Filter
//            // Mono : webflux, spring 5에 추가됨, 비동기 서버 지원, 단위값 반환
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                if (config.isPostLogger()) {
//                    log.info("Global Filter End: response code -> {}", response.getStatusCode());
//                }
//            }));
//        };

        // 매게변수에 exchange 는 spring 5 버전의 weflux 기반이다.
        // MVC에서 사용했던 HttpServlet, request 가지원 안됨.
        // ServerRequest, response 를 사용 해야 한다.
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter base message : {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging PRE Filter start : request id -> {}", request.getId());
            }
            // custom Post Filter
            // Mono : webflux, spring 5에 추가됨, 비동기 서버 지원, 단위값 반환
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Logging POST Filter End: response code -> {}", response.getStatusCode());
                }
            }));
//            두번재 인자로 인해 필터의 실행 순서를 제어할 수 있다.
        }, Ordered.LOWEST_PRECEDENCE);

        return filter;
    }

    @Data
    public static class Config {
        // configuration 정보를 담을 수 있다
        private  String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
