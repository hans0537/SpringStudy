package com.example.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
// spring security 6.1 버전 설정 참고 링크
// https://velog.io/@shon5544/Spring-Security-1.-%EC%84%A4%EC%A0%95
public class WebSecurity {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                // stateless한 rest api를 개발할 것이므로 csrf 공격에 대한 옵션은 꺼둔다.
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                )
                // 특정 URL에 대한 권한 설정.
                .authorizeHttpRequests((authorizeRequests) -> {
//                    authorizeRequests.requestMatchers("/user/**").authenticated();
//
//                    authorizeRequests.requestMatchers("/manager/**")
//                            // ROLE_은 붙이면 안 된다. hasAnyRole()을 사용할 때 자동으로 ROLE_이 붙기 때문이다.
//                            .hasAnyRole("ADMIN", "MANAGER");
//
//                    authorizeRequests.requestMatchers("/admin/**")
//                            // ROLE_은 붙이면 안 된다. hasRole()을 사용할 때 자동으로 ROLE_이 붙기 때문이다.
//                            .hasRole("ADMIN");
//
                    authorizeRequests.anyRequest().permitAll();
                })
//
//                .formLogin((formLogin) -> {
//                    /* 권한이 필요한 요청은 해당 url로 리다이렉트 */
//                    formLogin.loginPage("/login");
//                })

                .build();
    }


}
