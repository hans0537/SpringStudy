package com.example.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<Users> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<Users> retrieveUser(@PathVariable int id) {
        Users user = service.findOne(id);

        // 예외 처리를 통해 에러 코드도 클라이언트 측에 보내주고
        // 특정 예외 메세지를 보내 줄 수 있다
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // Hateoas 작업
        MyEntityModel<Users> entityModel = new MyEntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        entityModel.add(linkTo.withRel("all-users"));

        return entityModel;

    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users savedUser = service.save(user);

        // 생성후 서버측에서 생성한 회원의 id를 다시 받아올 수 있다.
        // Headers에 담겨져서 보내진다
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {

        Users user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));

        }
    }
}
