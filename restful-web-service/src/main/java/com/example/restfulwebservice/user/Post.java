package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // User : Post -> 1 : (0 ~ N), Main : Sub -> Parent : Child
    // Post 입장에서는 여러개의 게시물이 하나의 유저가 생성 가능함
    // Lazy => 지연 로딩방식, 게시물이 로딩되는 시점에 해당 유저의 정보를 가져온다
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // 외부에 데이터를 감추기 위한 어노테이션
    private Users user;
}
