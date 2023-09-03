package com.example.restfulwebservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository를 상속 받아 제공되는 함수를 사용가능하다
// <사용할 entity, pk 타입> 만 줘도 기본적인 CRUD 사용 가능
public interface UserRepository extends JpaRepository<Users, Integer> {

}
