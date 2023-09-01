package com.example.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;

public class MyEntityModel<T> extends EntityModel<T> {
    public MyEntityModel(T content) {
        super(content);
    }
}