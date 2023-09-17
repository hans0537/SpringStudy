package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import com.netflix.discovery.converters.Auto;
import feign.FeignException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    Environment env;
    RestTemplate restTemplate;
    OrderServiceClient orderServiceClient;


    @Autowired
    // BCryptPasswordEncoder 는 Bean에 등록 해준적이 없어서 에러가 생길것
    // 가장 먼저 실행되는 UserServiceApplication 에 Bean 등록
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           Environment env, RestTemplate restTemplate, OrderServiceClient orderServiceClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.restTemplate = restTemplate;
        this.orderServiceClient = orderServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        // userDto 객체를 Jpa를 통해 DB에 저장할 수 있는 객체인 Entity로 변환 할 수 있는 라이브러리
        ModelMapper mapper = new ModelMapper();
        // mapper의 설정에서 강력한 설정으로 변환
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

//        List<ResponseOrder> orders = new ArrayList<>();

        /* Rest template을 이용한 order-service로 값 받아오기 */
        // url 을 직접 하드코딩 하거나
        // String orderURL = "http://127.0.0.1:8000/order-service/%s/orders";
        // yml 설정 파일에서 받아오거나
//        String orderURL = String.format(env.getProperty("order-service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> ordersListResponse =
//                restTemplate.exchange(orderURL, HttpMethod.GET, null,
//                                        new ParameterizedTypeReference<List<ResponseOrder>>() {
//        });
//        // 받아온 값의 body로 orders 가져오기
//        List<ResponseOrder> ordersList = ordersListResponse.getBody();
//        userDto.setOrders(ordersList);

        /* Feign Client을 이용한 order-service로 값 받아오기 */
//        List<ResponseOrder> ordersList = null;
        /* Feign 예외 처리 핸들링 */
//        try {
//            orderServiceClient.getOrders(userId);
//        }catch (FeignException e) {
//            log.error(e.getMessage());
//        }

        // ErrorDecoder를 설정 해 놨기 때문에 오류가 생기면 바로 decoder에 걸린다
        List<ResponseOrder> ordersList = orderServiceClient.getOrders(userId);
        userDto.setOrders(ordersList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        return userDto;
    }
}
