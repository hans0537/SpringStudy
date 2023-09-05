package com.example.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@Slf4j
// apigateway 에 route조건절에 따라 uri 매핑을 해준다
@RequestMapping("/second-service/")
public class SecondServiceController {

//    application 설정 파일 정보 가져오기
    Environment env;

    @Autowired
    public SecondServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Second service.";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info(header);
        return "Hello World in Second Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server Port = {}", request.getServerPort());
        return String.format("Hi there. This is a message from second service on PORT %s"
                , env.getProperty("local.server.port"));
    }
}
