package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;

// Bean 직접 설정
// DI dependency injection
// @Autowired @Service @Repository 어노테이션으로도 설정 가능

// 그럼 어노테이션과 이렇게 직접 설정 차이점
// 주로 정형화된 일반적인 Controller, Service, Repository 는 컴포넌트 스캔 즉 어노테이션 사용
// 정형화 되지 않은, 상황에 따라 구현 클래스를 변경해야 한다면 이렇게 SpringConfig 파일 설정

@Configuration
public class SpringConfig {

//    private EntityManager em;
////    private DataSource dataSource;
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }
//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }

}
