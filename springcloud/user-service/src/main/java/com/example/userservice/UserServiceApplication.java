package com.example.userservice;

import com.example.userservice.error.FeignErrorDecoder;
import com.netflix.discovery.EurekaNamespace;
import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // Feign client (RestTemplate 대신 사용)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @LoadBalanced // 주소체계를 MSA 서비스 네임으로 하기 위함 127.0.0.1:8000 => user-service...
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    // Feign 관련 로그출력하기 위한 bean 등록
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // 모든 레벨 등록
    }

}
