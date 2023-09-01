package com.example.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok
// Getter, Setter 사용가능
@Data
// 생성자 메소드 만들어줌
@AllArgsConstructor
// default 생성자
@NoArgsConstructor 
public class HelloWorldBean {

    private String message;

}
