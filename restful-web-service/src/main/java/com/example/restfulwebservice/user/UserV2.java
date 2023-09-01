package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 특정 데이터를 안보여주기 위함
//@JsonIgnoreProperties(value={"password"})
@JsonFilter("UserInfoV2")
public class UserV2 extends User{
    private String grade;
}
