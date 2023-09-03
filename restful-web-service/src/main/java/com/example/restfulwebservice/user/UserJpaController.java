package com.example.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    @Autowired
    public UserRepository userRepository;

    @Autowired PostRepository postRepository;

    @GetMapping("/users")
    public List<Users> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public MyEntityModel<Users> retrieveUser(@PathVariable int id) {
        Optional<Users> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // Hateoas 작업
        MyEntityModel<Users> entityModel = new MyEntityModel<>(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        entityModel.add(linkTo.withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);

    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users savedUser = userRepository.save(user);

        // 생성후에 headers에 생성된 유저의 아이디를 바로 넘겨줄 수 있다
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostByUser(@PathVariable int id) {
        Optional<Users> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<Users> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // 게시물의 사용자 정보를 주입후 저장
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        // 생성후에 headers에 생성된 유저의 아이디를 바로 넘겨줄 수 있다
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
