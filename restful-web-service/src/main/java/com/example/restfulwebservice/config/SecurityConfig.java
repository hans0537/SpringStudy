//package com.example.restfulwebservice.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//
//// configuration 어노테이션을 통해 spring boot 설정 파일을 읽게 됨
//// security 설정을 위한 클래스 상속
//@Configuration
//public class SecurityConfig extends WebSecurityConfiguration {
//
//
//    @Autowired
//    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                // {noop} 인코딩 없이 진행한다. 인코딩 진행하면서 오류가 발생 할 수 도 있기 떄문
//                .password("{noop}asdf1234")
//                .roles("USER");
//    }
//}
