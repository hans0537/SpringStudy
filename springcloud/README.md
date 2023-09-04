# MSA Spring Cloud


## discoveryservice  

### spring eureka server (사용자가 요청할때 로드밸런싱을 통해 어떤 서비스로 가야할지 알려주는 서버)
  - 여러 서비스를 등록해야 하는 절차가 필요
  
  - yml 수정
  ```
    // 서버(포트번호) 와 서비스 이름을 지정
    server:
      port: 8761

    spring:
      application:
        name: discoveryservice

    // 서비스를 자동 등록하는데 discoveryservice는 서비스를 관리 하는 프로젝트이므로
    // 등록이 필요 없으므로 두 조건을 false
    eureka:
      client:
        register-with-eureka: false
        fetch-registry: false
        
  ```

  - Eureka server 등록
  ```
  @SpringBootApplication
  // 어노테이션 추가
  @EnableEurekaServer
  public class DiscoveryserviceApplication {

      public static void main(String[] args) {
          SpringApplication.run(DiscoveryserviceApplication.class, args);
      }

  }
  ```

  
## user-service (새로운 Micro service 를 하나 만든다)

- eureka discovery client 가 필요 
- 나머지 dependency 도 추가

- 어노테이션으로 등록 @EnableDiscoveryClient