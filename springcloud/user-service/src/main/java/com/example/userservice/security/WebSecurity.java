package com.example.userservice.security;

import com.example.userservice.service.UserService;
import com.example.userservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
// spring security 6.1 버전 설정 참고 링크
// https://velog.io/@shon5544/Spring-Security-1.-%EC%84%A4%EC%A0%95
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    @Autowired
    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    // select pwd from users where email = ?
    // db_pwd(encrypted) == input_pwd(encrypted)
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception  {

        // stateless한 rest api를 개발할 것이므로 csrf 공격에 대한 옵션은 꺼둔다.
        http.csrf().disable();

        // acutator는 모두 허용
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();


        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("192.168.100.74")
                .and()
                .addFilter(getAuthenticationFilter());
        http.headers().frameOptions().disable();

    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(), userService, env);
//        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
